package yerong.wedle.todo.dto;

import lombok.Getter;

@Getter
public class UpdateTodoTaskRequest {
    Long todoId;
    String task;
}
