package yerong.wedle.todo.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.todo.domain.Todo;
import yerong.wedle.todo.domain.TodoList;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTodoListAndDate(TodoList todoList, LocalDate date);
}
