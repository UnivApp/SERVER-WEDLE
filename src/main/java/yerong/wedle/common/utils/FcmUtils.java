package yerong.wedle.common.utils;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FcmUtils {

    private static final int FCM_PUSH_LIMIT_SIZE = 500;
    private static final long ONE_WEEK_IN_SECONDS = 60 * 60 * 24 * 7;
    private static final long EXPIRED_TIME_FOR_UNIX = new Date().getTime() / 1000 + ONE_WEEK_IN_SECONDS;

    public static void broadCast(final List<String> registrationTokens, String title, String body) {
        limitSizeValidate(registrationTokens);

        log.info("registrationTokens = {}", registrationTokens.get(0));
        log.info("title = {}", title);
        log.info("body = {}", body);

        if (title == null || title.isEmpty()) {
            log.error("제목이 비어 있습니다.");
            return;
        }
        if (body == null || body.isEmpty()) {
            log.error("본문이 비어 있습니다.");
            return;
        }

        long currentTime = new Date().getTime() / 1000;
        if (EXPIRED_TIME_FOR_UNIX <= currentTime) {
            log.error("APNs 만료 시간 설정이 잘못되었습니다.");
        }

        MulticastMessage message = buildMessage(registrationTokens, title, body);

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            pushSuccessValidate(registrationTokens, response);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 메시지 전송 중 예외 발생: {}", e.getMessage(), e);
            if (e.getErrorCode() != null) {
                log.error("Error Code: {}", e.getErrorCode());
            }
        }
    }

    private static MulticastMessage buildMessage(List<String> registrationTokens, String title, String body) {
        return MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setAlert(body)
                                .build())
                        .putHeader("apns-expiration", Long.toString(EXPIRED_TIME_FOR_UNIX))
                        .build())
                .addAllTokens(registrationTokens)
                .build();
    }

    private static void limitSizeValidate(final List<String> registrationTokens) {
        if (registrationTokens.size() > FCM_PUSH_LIMIT_SIZE) {
            throw new IllegalArgumentException("FCM push 알림 수신자는 최대 500명입니다.");
        }
    }

    private static void pushSuccessValidate(final List<String> registrationTokens, final BatchResponse response) {
        log.info("{} messages were sent successfully.", response.getSuccessCount());
        if (response.getFailureCount() > 0) {
            log.error("{} messages failed to send.", response.getFailureCount());
            response.getResponses().forEach(sendResponse -> {
                if (!sendResponse.isSuccessful()) {
                    String messageId = sendResponse.getMessageId();
                    String errorMessage = sendResponse.getException() != null ? sendResponse.getException().getMessage() : "Unknown error";

                    log.error("Failed to send message to token: {}. Error: {}",
                            messageId != null ? messageId : "null", errorMessage);
                }
            });
        }
    }
}
