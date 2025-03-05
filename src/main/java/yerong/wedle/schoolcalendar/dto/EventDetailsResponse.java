package yerong.wedle.schoolcalendar.dto;

import lombok.Data;

@Data
public class EventDetailsResponse {
    private String eventName;
    private String content;

    private boolean oneGradeEventYN;
    private boolean twoGradeEventYN;
    private boolean threeGradeEventYN;
    private boolean fourGradeEventYN;
    private boolean fiveGradeEventYN;
    private boolean sixGradeEventYN;

    public EventDetailsResponse(String eventName, String content, boolean oneGradeEventYN, boolean twoGradeEventYN,
                                boolean threeGradeEventYN, boolean fourGradeEventYN,
                                boolean fiveGradeEventYN, boolean sixGradeEventYN) {
        this.eventName = eventName;
        this.content = content;
        this.oneGradeEventYN = oneGradeEventYN;
        this.twoGradeEventYN = twoGradeEventYN;
        this.threeGradeEventYN = threeGradeEventYN;
        this.fourGradeEventYN = fourGradeEventYN;
        this.fiveGradeEventYN = fiveGradeEventYN;
        this.sixGradeEventYN = sixGradeEventYN;
    }
}
