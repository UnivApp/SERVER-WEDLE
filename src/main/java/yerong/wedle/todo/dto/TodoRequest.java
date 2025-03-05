package yerong.wedle.todo.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TodoRequest {
    private String task;
    private LocalDate date;
//    private Visibility visibility;
}
