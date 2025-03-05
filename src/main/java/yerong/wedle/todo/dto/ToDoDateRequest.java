package yerong.wedle.todo.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ToDoDateRequest {
    private LocalDate date;
}
