package yerong.wedle.timetable.personalTimetable.dto;

import lombok.Data;

@Data
public class PersonalScheduleRequest {
    private String day;
    private String startTime;
    private String endTime;
    private String title;
    private String color;

    public PersonalScheduleRequest(String day, String startTime, String endTime, String title, String color) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.color = color;
    }
}
