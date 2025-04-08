package yerong.wedle.chat.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yerong.wedle.chat.domain.chatting.ChatRoom;
import yerong.wedle.member.domain.Member;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select cr from ChatRoom cr join cr.sender s join cr.recipient r where cr.id = :chatRoomId and (s.memberId = :memberId or r.memberId = :memberId)")
    Optional<ChatRoom> findByIdAndMemberId(@Param("chatRoomId") Long chatRoomId, @Param("memberId") Long memberId);

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.sender s JOIN FETCH cr.recipient r WHERE cr.id = :id")
    Optional<ChatRoom> findByIdWithMembers(@Param("id") Long id);

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.sender s JOIN FETCH cr.recipient r WHERE s.memberId =:memberId OR r.memberId =:memberId")
    List<ChatRoom> findWithMembersByMemberId(@Param("memberId") Long memberId);

    Optional<ChatRoom> findBySenderAndRecipient(Member sender, Member recipient);

}

