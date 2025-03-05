package yerong.wedle.todo.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class TodoListResponse {
    private LocalDate date;
    private List<TodoResponse> todoResponses;

    public TodoListResponse(LocalDate date, List<TodoResponse> responses) {
        this.date = date;
        todoResponses = responses;
    }
}
