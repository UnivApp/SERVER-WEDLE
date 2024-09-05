package yerong.wedle.category.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventResponse {

    private String name;
    private String eventType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String lineUp;
    private String descriptions;
    private Set<EventImageResponse> photos;
}
