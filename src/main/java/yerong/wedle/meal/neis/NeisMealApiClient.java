package yerong.wedle.meal.neis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class NeisMealApiClient {

    private final WebClient webClient;

    @Value("${neis.key}")
    private String API_KEY;

    public NeisMealApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://open.neis.go.kr/hub/mealServiceDietInfo").build();
    }

    public List<NeisMealResponse> getMealByDate(String schoolCode, LocalDate date, String atpt) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = String.format(
                "?Type=json&pIndex=1&pSize=100&SD_SCHUL_CODE=%s&KEY=%s&ATPT_OFCDC_SC_CODE=%s&MLSV_YMD=%s",
                schoolCode, API_KEY, atpt, formattedDate);
        System.out.println("url = " + url);
        NeisMealApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error Response: " + errorBody);
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            });
                })
                .bodyToMono(NeisMealApiResponse.class)
                .doOnError(e -> {
                    log.error("Error occurred: " + e.getMessage());
                })
                .block();
        List<NeisMealResponse> neisMealRespons = response.getSchoolList();
        return neisMealRespons;
    }
}