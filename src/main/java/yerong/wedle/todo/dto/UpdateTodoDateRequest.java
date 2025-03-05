package yerong.wedle.todo.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UpdateTodoDateRequest {
    private Long todoId;
    private LocalDate date;
}
