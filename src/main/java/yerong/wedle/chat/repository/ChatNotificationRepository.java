package yerong.wedle.chat.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.chat.domain.notification.ChatNotification;
import yerong.wedle.member.domain.Member;

public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ChatNotification cn WHERE cn.id =:id AND cn.recipientId =:recipientId")
    int deleteByIdAndRecipientId(@Param("id") Long id, @Param("recipientId") Long recipientId);

    void deleteByRecipientId(Long recipientId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ChatNotification cn WHERE cn.recipientId =:recipientId AND cn.chatRoomId =:chatRoomId")
    void deleteByRecipientIdAndChatRoomId(@Param("recipientId") Long recipientId, @Param("chatRoomId") Long chatRoomId);

    Slice<ChatNotification> findSliceChatNotificationsBySender(Member sender, Long cursorId, Pageable pageable);
}
