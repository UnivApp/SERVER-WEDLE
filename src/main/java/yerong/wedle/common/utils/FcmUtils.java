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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FcmUtils {

    private static final int FCM_PUSH_LIMIT_SIZE = 500;
    private static final long ONE_WEEK = (long) 60 * 60 * 24 * 7;
    private static final long EXPIRED_TIME_FOR_UNIX = new Date(new Date().getTime() + ONE_WEEK).getTime();

    public static void broadCast(final List<String> registrationTokens, String title, String body) {
        limitSizeValidate(registrationTokens);

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder().setAlert(body).build())
                    .putHeader("apns-expiration", Long.toString(EXPIRED_TIME_FOR_UNIX))
                .build())
                .addAllTokens(registrationTokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            pushSuccessValidate(registrationTokens, response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            System.out.println("FCM 메시지 전송 중 예외 발생: " + e.getMessage());
        }
    }

    private static void limitSizeValidate(final List<String> registrationTokens) {
        if (registrationTokens.size() > FCM_PUSH_LIMIT_SIZE) {
            throw new IllegalArgumentException("FCM push 알림 수신자는 최대 500명입니다.");
        }
    }

    private static void pushSuccessValidate(final List<String> registrationTokens, final BatchResponse response) {
        System.out.println(response.getSuccessCount() + " messages were sent successfully.");
        if (response.getFailureCount() > 0) {
            System.out.println(response.getFailureCount() + " messages failed to send.");
            response.getResponses().forEach(sendResponse -> {
                if (!sendResponse.isSuccessful()) {
                    System.out.println("Failed to send message to token: " + sendResponse.getMessageId());
                    System.out.println("Error: " + sendResponse.getException().getMessage());
                }
            });
        }
    }
}
