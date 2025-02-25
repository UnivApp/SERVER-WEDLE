package yerong.wedle.neis;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class NeisApiClient {

    private final WebClient webClient;

    @Value("${neis.key}")
    private String API_KEY;

    public NeisApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://open.neis.go.kr/hub/schoolInfo").build();
    }

    public List<NeisSchoolResponse> searchSchool(String keyword) {
        String url = String.format("?Type=json&pIndex=1&pSize=100&SCHUL_NM=%s&KEY=%s", keyword, API_KEY);

        System.out.println("keyword: " + keyword);
        System.out.println("url : " + url);
        String fullUrl = "https://open.neis.go.kr/hub/schoolInfo" + url;
        System.out.println("Full URL: " + fullUrl);

        NeisApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error Response: " + errorBody); // 에러 응답 출력
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            });
                })
                .bodyToMono(NeisApiResponse.class)
                .doOnError(e -> {
                    System.err.println("Error occurred: " + e.getMessage()); // 예외 메시지 출력
                })
                .block();
        System.out.println("API Response: " + response);
        System.out.println("response.getSchoolInfo(): " + response.getSchoolInfo());

        List<NeisSchoolResponse> neisSchoolResponses = response.getSchoolList();
        for (NeisSchoolResponse neisSchoolResponse : neisSchoolResponses) {
            System.out.println("school: " + neisSchoolResponse.getSchoolName());
        }
        return neisSchoolResponses;
    }
}