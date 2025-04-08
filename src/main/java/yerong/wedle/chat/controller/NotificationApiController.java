package yerong.wedle.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.chat.dto.notification.NotificationResponse.ChatNotificationResponse;
import yerong.wedle.chat.service.NotificationService;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping()
public class NotificationApiController {

    private final NotificationService notificationService;

    @GetMapping("/api/notifications")
    public ResponseEntity<String> findChatNotifications(@RequestParam(required = false) Long cursorId,
                                                        @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Slice<ChatNotificationResponse> result
                = notificationService.getChatNotificationsByMemberId(cursorId, pageable);
        return ResponseEntity.ok("메시지 알림 목록 조회에 성공하였습니다");
    }

    @DeleteMapping("/api/notifications")
    public ResponseEntity<?> deleteNotifications() {

        notificationService.deleteAllNotificationsOfMember();
        return ResponseEntity.ok("채팅 메시지 알림 삭제에 성공하였습니다");
    }

    @DeleteMapping("/api/notifications/{notificationId}")
    public ResponseEntity<?> deleteSingleNotification(@PathVariable("notificationId") Long notificationId) {

        notificationService.deleteSingleChatNotification(notificationId);
        return ResponseEntity.ok("채팅 메시지 알림 삭제에 성공하였습니다");
    }
}