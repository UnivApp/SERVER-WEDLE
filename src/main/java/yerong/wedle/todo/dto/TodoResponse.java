package yerong.wedle.todo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TodoResponse {
    String task;
    private LocalDate date;
    private boolean completed;

    public TodoResponse(LocalDate date, String task, boolean completed) {
        this.date = date;
        this.task = task;
        this.completed = completed;
    }
}
