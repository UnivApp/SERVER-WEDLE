//package yerong.wedle.chat.repository;
//
//import static yerong.wedle.chat.domain.chatting.QChatRoom.chatRoom;
//
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.CaseBuilder;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import java.util.List;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.domain.SliceImpl;
//import yerong.wedle.chat.dto.chatting.ChatResponse.ChatRoomResponse;
//
//public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    public ChatRoomRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public Slice<ChatRoomResponse> findSliceChatRoomsByMemberId(Long memberId, Pageable pageable) {
//
//        JPAQuery<ChatRoomResponse> query = queryFactory
//                .select(Projections.constructor(ChatRoomResponse.class,
//
//                        chatRoom.id,
//                        new CaseBuilder()
//                                .when(chatRoom.sender.memberId.eq(memberId))
//                                .then(chatRoom.recipient.nickname.concat(" 와(과)의 채팅방입니다."))
//                                .otherwise(chatRoom.sender.nickname.concat(" 와(과)의 채팅방입니다.")),
//                        new CaseBuilder()
//                                .when(chatRoom.sender.memberId.eq(memberId))
//                                .then(chatRoom.recipient.profileImageUrl)
//                                .otherwise(chatRoom.sender.profileImageUrl),
//                        new CaseBuilder()
//                                .when(chatRoom.sender.memberId.eq(memberId))
//                                .then(chatRoom.recipient.nickname)
//                                .otherwise(chatRoom.sender.nickname),
//                        chatRoom.createdAt
//                ))
//                .from(chatRoom)
//                .where(chatRoom.sender.memberId.eq(memberId)
//                        .or(chatRoom.recipient.memberId.eq(memberId)))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1);
//
//        List<ChatRoomResponse> result = query.fetch();
//
//        boolean hasNext = false;
//        if (result.size() > pageable.getPageSize()) {
//            result.remove(pageable.getPageSize());
//            hasNext = true;
//        }
//
//        return new SliceImpl<>(result, pageable, hasNext);
//    }
//}
