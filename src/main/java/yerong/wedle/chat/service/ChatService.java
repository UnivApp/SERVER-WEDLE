package yerong.wedle.chat.service;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.dto.chatting.ChatRequest.CreateNewChatRoomRequest;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatMessageListResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatRoomsResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.CreateNewChatRoomResponse;


public interface ChatService {
    CreateNewChatRoomResponse createOrGetChatRoom(CreateNewChatRoomRequest requestDto);

    ChatRoomsResponse getChatRooms();

    ChatMessageListResponse getChatMessages(Long chatRoomId, String cursor, Pageable pageable);

    void sendMessage(Message message);

    void saveChatRoomParticipantToRedis(Long chatRoomId);

    void deleteChatRoomParticipantFromRedis();

    void updateUnreadMessages(Long chatRoomId);

    Optional<Long> getOtherMemberIdByChatRoomId(Long chatRoomId);
}
