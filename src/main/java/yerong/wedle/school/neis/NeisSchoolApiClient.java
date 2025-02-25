package yerong.wedle.school.neis;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class NeisSchoolApiClient {

    private final WebClient webClient;

    @Value("${neis.key}")
    private String API_KEY;

    public NeisSchoolApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://open.neis.go.kr/hub/schoolInfo").build();
    }

    public List<NeisSchoolResponse> searchSchool(String keyword) {
        String url = String.format("?Type=json&pIndex=1&pSize=100&SCHUL_NM=%s&KEY=%s", keyword, API_KEY);

        NeisSchoolApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error Response: " + errorBody);
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            });
                })
                .bodyToMono(NeisSchoolApiResponse.class)
                .doOnError(e -> {
                    log.error("Error occurred: " + e.getMessage());
                })
                .block();
        List<NeisSchoolResponse> neisSchoolResponses = response.getSchoolList();
        return neisSchoolResponses;
    }
}