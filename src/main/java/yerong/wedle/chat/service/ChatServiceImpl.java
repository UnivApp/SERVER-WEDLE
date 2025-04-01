package yerong.wedle.chat.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.chat.domain.chatting.ChatMessage;
import yerong.wedle.chat.domain.chatting.ChatRoom;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.dto.chatting.ChatRequest.CreateNewChatRoomRequest;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatMessageListResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatMessageResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatRoomResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatRoomsResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.CreateNewChatRoomResponse;
import yerong.wedle.chat.event.SendMessageEvent;
import yerong.wedle.chat.redis.ChatRoomParticipant;
import yerong.wedle.chat.redis.ChatRoomParticipantRedisRepository;
import yerong.wedle.chat.repository.ChatMessageRepository;
import yerong.wedle.chat.repository.ChatRoomRepository;
import yerong.wedle.chat.util.ChatUtil;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {


    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomParticipantRedisRepository chatRoomParticipantRedisRepository;
    private final MongoTemplate mongoTemplate;
    private final NotificationService notificationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CreateNewChatRoomResponse createOrGetChatRoom(CreateNewChatRoomRequest requestDto) {
        String socialId = getCurrentUserId();
        Member sender = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long receiverId = requestDto.getReceiverId();
        Member receiver = memberRepository.findById(receiverId).orElseThrow(MemberNotFoundException::new);

        Optional<ChatRoom> findChatRoomOpt
                = chatRoomRepository.findBySenderAndRecipient(sender, receiver);

        if (findChatRoomOpt.isPresent()) {
            return new CreateNewChatRoomResponse(false, findChatRoomOpt.get().getId());
        }

        List<Member> members = memberRepository.findByMemberIdOrMemberId(sender.getMemberId(), receiver.getMemberId());
        validateMembers(members);

        return createNewChatRoom(sender, receiver);
    }

    private void validateMembers(List<Member> members) {
        if (members.size() != 2) {
            throw new MemberNotFoundException();
        }
    }

    private CreateNewChatRoomResponse createNewChatRoom(Member sender, Member recipient) {
        ChatRoom chatRoom = ChatRoom.createNewChatRoom(sender, recipient);
        ChatRoom createdChatRoom = chatRoomRepository.save(chatRoom);
        return new CreateNewChatRoomResponse(true, createdChatRoom.getId());
    }

    @Override
    public ChatRoomsResponse getChatRooms() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long memberId = member.getMemberId();

        List<ChatRoom> chatRooms = chatRoomRepository.findWithMembersByMemberId(memberId);

        List<ChatRoomResponse> chatRoomRespDtos = chatRooms.stream()
                .map(chatRoom -> createChatRoomResponse(chatRoom, memberId))
                .collect(Collectors.toList());

        List<ChatRoomResponse> processedChatRooms = processChatRooms(chatRoomRespDtos, memberId);

        String memberName = findMemberName(chatRooms.get(0), memberId);

        return new ChatRoomsResponse(memberName, sortChatRoomsByLastMessageTime(processedChatRooms));
    }

    private ChatRoomResponse createChatRoomResponse(ChatRoom chatRoom, Long memberId) {
        Member otherMember = getOtherMember(chatRoom, memberId);
        return new ChatRoomResponse(chatRoom, otherMember);
    }

    private Member getOtherMember(ChatRoom chatRoom, Long memberId) {
        if (chatRoom.getSender().getMemberId().equals(memberId)) {
            return chatRoom.getRecipient();
        }
        return chatRoom.getSender();
    }

    private List<ChatRoomResponse> processChatRooms(List<ChatRoomResponse> chatRooms, Long memberId) {

        for (ChatRoomResponse chatRoom : chatRooms) {
            processSingleChatRoom(chatRoom, memberId);
        }
        return chatRooms;
    }

    private void processSingleChatRoom(ChatRoomResponse chatRoomRespDto, Long memberId) {
        long unreadMessageCount = countUnReadMessages(chatRoomRespDto.getChatRoomId(), memberId);
        chatRoomRespDto.changeUnReadMessageCount(unreadMessageCount);
        updateChatRoomWithLastMessageIfExists(chatRoomRespDto);
    }

    private void updateChatRoomWithLastMessageIfExists(ChatRoomResponse chatRoomRespDto) {
        Optional<ChatMessage> lastMessageOpt = findLastMessageInChatRoom(chatRoomRespDto.getChatRoomId());
        lastMessageOpt.ifPresent((lastMessage) -> updateChatRoomWithLastMessage(chatRoomRespDto, lastMessage));
    }

    private Optional<ChatMessage> findLastMessageInChatRoom(Long chatRoomId) {
        return chatMessageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoomId);
    }

    private void updateChatRoomWithLastMessage(ChatRoomResponse chatRoomRespDto, ChatMessage lastMessage) {
        chatRoomRespDto.changeLastMessage(lastMessage.getContent(), lastMessage.getCreatedAt());
    }

    private long countUnReadMessages(Long chatRoomId, Long memberId) {
        Query query = new Query(Criteria.where("chatRoomId").is(chatRoomId)
                .and("readCount").is(1)
                .and("senderId").ne(memberId));

        return mongoTemplate.count(query, ChatMessage.class);
    }

    private String findMemberName(ChatRoom chatRoom, Long memberId) {
        if (chatRoom.getSender().getMemberId().equals(memberId)) {
            return chatRoom.getSender().getNickname();
        }

        if (chatRoom.getRecipient().getSocialId().equals(memberId)) {
            return chatRoom.getRecipient().getNickname();
        }

        return "Unknown";
    }

    private List<ChatRoomResponse> sortChatRoomsByLastMessageTime(List<ChatRoomResponse> chatRooms) {
        return chatRooms.stream()
                .sorted(Comparator.comparing(ChatRoomResponse::getLastMessageTime,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageListResponse getChatMessages(Long chatRoomId, String lastChatMessageId,
                                                   Pageable pageable) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();
        validateChatRoom(senderId, chatRoomId);

        Slice<ChatMessage> chatMessageSlice = getChatMessageSlice(chatRoomId, lastChatMessageId, pageable);

        List<ChatMessageResponse> chatMessageRespDtos = convertToChatMessageRespDto(chatMessageSlice.getContent(),
                senderId);

        Slice<ChatMessageResponse> chatMessageRespDtoSlice
                = new SliceImpl<>(chatMessageRespDtos, pageable, chatMessageSlice.hasNext());

        return new ChatMessageListResponse(senderId, chatRoomId, chatMessageRespDtoSlice);
    }

    private void validateChatRoom(Long memberId, Long chatRoomId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByIdAndMemberId(chatRoomId, memberId);
        if (chatRoom.isEmpty()) {
            throw new MemberNotFoundException();
        }
    }

    private Slice<ChatMessage> getChatMessageSlice(Long chatRoomId, String lastChatMessageId, Pageable pageable) {
        return lastChatMessageId == null
                ? chatMessageRepository.findByChatRoomIdOrderByCreatedAtDesc(chatRoomId, pageable)
                : findChatMessagesWithObjectId(chatRoomId, lastChatMessageId, pageable);
    }

    public Slice<ChatMessage> findChatMessagesWithObjectId(Long chatRoomId, String lastChatMessageId,
                                                           Pageable pageable) {
        Query query = getChatMessagesQuery(chatRoomId, lastChatMessageId, pageable);
        List<ChatMessage> messages = mongoTemplate.find(query, ChatMessage.class);
        return createChatMessagesSlice(messages, pageable);
    }

    private Query getChatMessagesQuery(Long chatRoomId, String lastChatMessageId, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatRoomId").is(chatRoomId)
                .andOperator(Criteria.where("_id").lt(new ObjectId(lastChatMessageId))));
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        query.limit(pageable.getPageSize() + 1);
        return query;
    }

    private Slice<ChatMessage> createChatMessagesSlice(List<ChatMessage> messages, Pageable pageable) {
        boolean hasNext = messages.size() > pageable.getPageSize();
        if (hasNext) {
            messages = messages.subList(0, pageable.getPageSize());
        }
        return new SliceImpl<>(messages, pageable, hasNext);
    }

    private List<ChatMessageResponse> convertToChatMessageRespDto(List<ChatMessage> chatMessages, Long senderId) {
        List<ChatMessageResponse> chatMessageRespDtos = chatMessages.stream()
                .map(chatMessage -> new ChatMessageResponse(chatMessage, senderId))
                .collect(Collectors.toList());
        Collections.reverse(chatMessageRespDtos);
        return chatMessageRespDtos;
    }

    @Override
    @Transactional
    public void sendMessage(Message message) {

        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();

        Long chatRoomId = message.getChatRoomId();
        boolean allMembersParticipatingInChatRoom = isAllMembersParticipatingInChatRoom(chatRoomId);
        processMessage(message, senderId, allMembersParticipatingInChatRoom);

        ChatMessage chatMessage = message.convertToChatMessage();
        chatMessageRepository.save(chatMessage);

        if (!allMembersParticipatingInChatRoom) {
            notificationService.sendNotification(message, senderId, chatRoomId);
        }

        applicationEventPublisher.publishEvent(
                new SendMessageEvent(this, chatRoomId, message));
    }

    private boolean isAllMembersParticipatingInChatRoom(Long chatRoomId) {
        List<ChatRoomParticipant> chatRoomParticipants = getChatRoomParticipants(chatRoomId);
        return chatRoomParticipants.size() == ChatUtil.MAX_PARTICIPANTS_PER_CHATROOM;
    }

    private void processMessage(Message message, Long senderId, boolean allMembersParticipatingInChatRoom) {
        int readCount = calculateReadCount(allMembersParticipatingInChatRoom);
        updateMessageBeforeSending(message, senderId, readCount);
    }

    private int calculateReadCount(boolean allMembersParticipatingInChatRoom) {
        return allMembersParticipatingInChatRoom ? 0 : 1;
    }

    private void updateMessageBeforeSending(Message message, Long senderId, int readCount) {
        message.prepareMessageForSending(senderId, LocalDateTime.now(), readCount);
    }

    @Override
    public void saveChatRoomParticipantToRedis(Long chatRoomId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();

        ChatRoomParticipant chatRoomParticipant = new ChatRoomParticipant(senderId, chatRoomId);
        chatRoomParticipantRedisRepository.save(chatRoomParticipant);
    }

    @Override
    public void deleteChatRoomParticipantFromRedis() {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();

        List<ChatRoomParticipant> chatRoomParticipants
                = chatRoomParticipantRedisRepository.findByMemberId(senderId);

        if (chatRoomParticipants != null && !chatRoomParticipants.isEmpty()) {
            chatRoomParticipantRedisRepository.deleteAll(chatRoomParticipants);
        }
    }

    @Override
    public void updateUnreadMessages(Long chatRoomId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();

        Query query = new Query(Criteria.where("chatRoomId")
                .is(chatRoomId)
                .and("readCount").is(1)
                .and("senderId").ne(senderId));

        Update update = new Update().set("readCount", 0);
        mongoTemplate.updateMulti(query, update, ChatMessage.class);
    }

    @Override
    public Optional<Long> getOtherMemberIdByChatRoomId(Long chatRoomId) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Long senderId = member.getMemberId();

        List<ChatRoomParticipant> chatRoomParticipants = getChatRoomParticipants(chatRoomId);

        return chatRoomParticipants == null ? Optional.empty() : findOtherMember(chatRoomParticipants, senderId);
    }

    private List<ChatRoomParticipant> getChatRoomParticipants(Long chatRoomId) {
        return chatRoomParticipantRedisRepository.findByChatRoomId(chatRoomId);
    }

    private Optional<Long> findOtherMember(List<ChatRoomParticipant> chatRoomParticipants, Long senderId) {
        return chatRoomParticipants.stream()
                .map(ChatRoomParticipant::getMemberId)
                .filter(id -> !id.equals(senderId))
                .findFirst();
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
