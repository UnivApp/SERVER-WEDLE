package yerong.wedle.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.todo.dto.TodoListResponse;
import yerong.wedle.todo.dto.TodoRequest;
import yerong.wedle.todo.dto.TodoResponse;
import yerong.wedle.todo.dto.UpdateTodoCompletionRequest;
import yerong.wedle.todo.dto.UpdateTodoDateRequest;
import yerong.wedle.todo.dto.UpdateTodoTaskRequest;
import yerong.wedle.todo.service.TodoService;

@Tag(name = "To Do List API", description = "To Do List API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo-list")
public class TodoApiController {
    private final TodoService todoService;

    @Operation(
            summary = "할일 추가",
            description = "할일을 추가합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "할일 생성 성공"),
                    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            }
    )
    @PostMapping("/todo")
    public ResponseEntity<TodoResponse> addTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse todoResponse = todoService.addTodo(todoRequest);
        return ResponseEntity.ok(todoResponse);
    }

    @Operation(
            summary = "할일 이름 수정",
            description = "할일의 이름을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "할일 이름 수정 성공"),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없습니다."),
            }
    )
    @PutMapping("/todo/update-task")
    public ResponseEntity<TodoResponse> updateTodoTask(@RequestBody UpdateTodoTaskRequest todoRenameRequest) {
        TodoResponse todoResponse = todoService.updateTodoTask(todoRenameRequest);
        return ResponseEntity.ok(todoResponse);
    }

    @Operation(
            summary = "할일 날짜 수정",
            description = "할일 날짜를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "할일 날짜 수정 성공"),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없습니다."),
            }
    )
    @PutMapping("/todo/update-date")
    public ResponseEntity<TodoResponse> updateTodoDate(@RequestBody UpdateTodoDateRequest updateTodoDateRequest) {
        TodoResponse todoResponse = todoService.updateTodoDate(updateTodoDateRequest);
        return ResponseEntity.ok(todoResponse);
    }

    @Operation(
            summary = "할일 성공 여부 수정",
            description = "할일 성공 여부를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "할일 성공 여부 수정 성공"),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없습니다."),
            }
    )
    @PutMapping("/todo/completion")
    public ResponseEntity<TodoResponse> updateTodoCompletion(
            @RequestBody UpdateTodoCompletionRequest updateTodoCompletionRequest) {
        TodoResponse todoResponse = todoService.updateTodoCompletion(updateTodoCompletionRequest);
        return ResponseEntity.ok(todoResponse);
    }

    @Operation(
            summary = "할일 삭제",
            description = "할일 삭제을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "할일 삭제 성공"),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없습니다."),
            }
    )
    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodoCompletion(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo가 성공적으로 삭제되었습니다.");
    }

    @Operation(
            summary = "특정 날짜의 할일 목록 조회",
            description = "특정 날짜에 해당하는 할일 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 날짜의 할일 목록 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "할일을 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."),
            }
    )
    @GetMapping("/todos/{date}")
    public ResponseEntity<TodoListResponse> getTodosByDate(@PathVariable LocalDate date) {
        TodoListResponse todoResponses = todoService.getTodosByDate(date);
        return ResponseEntity.ok(todoResponses);
    }
}
