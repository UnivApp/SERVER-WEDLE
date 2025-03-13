package yerong.wedle.todo.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.common.domain.Visibility;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.exception.UnauthorizedAccessException;
import yerong.wedle.member.repository.MemberRepository;
import yerong.wedle.todo.domain.Todo;
import yerong.wedle.todo.domain.TodoList;
import yerong.wedle.todo.dto.TodoListResponse;
import yerong.wedle.todo.dto.TodoRequest;
import yerong.wedle.todo.dto.TodoResponse;
import yerong.wedle.todo.dto.UpdateTodoCompletionRequest;
import yerong.wedle.todo.dto.UpdateTodoDateRequest;
import yerong.wedle.todo.dto.UpdateTodoTaskRequest;
import yerong.wedle.todo.exception.TodoNotFoundException;
import yerong.wedle.todo.repository.TodoListRepository;
import yerong.wedle.todo.repository.TodoRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class TodoService {
    private final MemberRepository memberRepository;
    private final TodoListRepository todoListRepository;
    private final TodoRepository todoRepository;

    public TodoResponse addTodo(TodoRequest todoRequest) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        Optional<TodoList> todoListOptional = todoListRepository.findByMember(member);
        TodoList todoList;
        if (todoListOptional.isEmpty()) {
            todoList = new TodoList(member);
            todoListRepository.save(todoList);
        } else {
            todoList = todoListOptional.get();
        }
        Todo todo = new Todo(todoRequest.getDate(), todoRequest.getTask(), todoList, Visibility.PRIVATE);
        todo.addTodoList(todoList);
        todoList.addTask(todo);

        todoRepository.save(todo);
        return convertToTodoResponse(todo);
    }

    public TodoResponse updateTodoTask(UpdateTodoTaskRequest updateTodoTaskRequest) {
        Todo todo = todoRepository.findById(updateTodoTaskRequest.getTodoId()).orElseThrow(TodoNotFoundException::new);
        String socialId = getCurrentUserId();
        if (!todo.getTodoList().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        todo.updateTask(updateTodoTaskRequest.getTask());
        todoRepository.save(todo);
        return convertToTodoResponse(todo);
    }

    public TodoResponse updateTodoDate(UpdateTodoDateRequest updateTodoDateRequest) {
        Todo todo = todoRepository.findById(updateTodoDateRequest.getTodoId()).orElseThrow(TodoNotFoundException::new);
        String socialId = getCurrentUserId();
        if (!todo.getTodoList().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        todo.updateDate(updateTodoDateRequest.getDate());
        todoRepository.save(todo);
        return convertToTodoResponse(todo);
    }

    public TodoResponse updateTodoCompletion(UpdateTodoCompletionRequest updateTodoCompletionRequest) {
        Todo todo = todoRepository.findById(updateTodoCompletionRequest.getTodoId())
                .orElseThrow(TodoNotFoundException::new);
        String socialId = getCurrentUserId();
        if (!todo.getTodoList().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        todo.updateCompleted(updateTodoCompletionRequest.isCompleted());
        todoRepository.save(todo);
        return convertToTodoResponse(todo);
    }

    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(TodoNotFoundException::new);
        String socialId = getCurrentUserId();
        if (!todo.getTodoList().getMember().getSocialId().equals(socialId)) {
            throw new UnauthorizedAccessException();
        }
        todoRepository.delete(todo);
    }

    private TodoResponse convertToTodoResponse(Todo todo) {
        return new TodoResponse(todo.getDate(), todo.getTask(), todo.isCompleted());
    }


    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }

    public TodoListResponse getTodosByDate(LocalDate date) {
        String socialId = getCurrentUserId();
        Member member = memberRepository.findBySocialId(socialId)
                .orElseThrow(MemberNotFoundException::new);

        TodoList todoList = todoListRepository.findByMember(member)
                .orElseThrow(TodoNotFoundException::new);

        List<Todo> todos = todoRepository.findByTodoListAndDate(todoList, date);
        List<TodoResponse> responses = todos.stream()
                .map(todo -> new TodoResponse(todo.getDate(), todo.getTask(), todo.isCompleted()))
                .collect(Collectors.toList());
        return new TodoListResponse(date, responses);
    }
}
