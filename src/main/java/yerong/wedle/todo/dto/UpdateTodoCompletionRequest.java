package yerong.wedle.todo.dto;

import lombok.Getter;

@Getter
public class UpdateTodoCompletionRequest {
    private Long todoId;
    private boolean completed;
}
