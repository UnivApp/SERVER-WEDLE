package yerong.wedle.schoolcalendar.neis;

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
public class NeisSchoolCalendarApiClient {

    private final WebClient webClient;

    @Value("${neis.key}")
    private String API_KEY;

    public NeisSchoolCalendarApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://open.neis.go.kr/hub/SchoolSchedule").build();
    }


    public List<NeisSchoolCalendarResponse> getScheduleByDate(String schoolCode, LocalDate date, String atpt) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = String.format(
                "?Type=json&pIndex=1&pSize=100&SD_SCHUL_CODE=%s&KEY=%s&ATPT_OFCDC_SC_CODE=%s&AA_YMD=%s",
                schoolCode, API_KEY, atpt, formattedDate);
        NeisSchoolCalendarApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error Response: " + errorBody);
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            });
                })
                .bodyToMono(NeisSchoolCalendarApiResponse.class)
                .doOnError(e -> {
                    log.error("Error occurred: " + e.getMessage());
                })
                .block();
        List<NeisSchoolCalendarResponse> neisSchoolCalendarResponse = response.getSchoolSchedule();
        return neisSchoolCalendarResponse;
    }

    public List<NeisSchoolCalendarResponse> getScheduleBetween(String schoolCode, LocalDate startDate,
                                                               LocalDate endDate,
                                                               String atpt) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String url = String.format(
                "?Type=json&pIndex=1&pSize=100&SD_SCHUL_CODE=%s&KEY=%s&ATPT_OFCDC_SC_CODE=%s&AA_FROM_YMD=%s&AA_TO_YMD=%s",
                schoolCode, API_KEY, atpt, formattedStartDate, formattedEndDate);
        NeisSchoolCalendarApiResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                log.error("Error Response: " + errorBody);
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            });
                })
                .bodyToMono(NeisSchoolCalendarApiResponse.class)
                .doOnError(e -> {
                    log.error("Error occurred: " + e.getMessage());
                })
                .block();
        List<NeisSchoolCalendarResponse> neisSchoolCalendarResponse = response.getSchoolSchedule();
        return neisSchoolCalendarResponse;
    }
}