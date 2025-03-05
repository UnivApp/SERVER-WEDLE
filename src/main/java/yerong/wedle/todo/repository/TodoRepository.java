package yerong.wedle.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.todo.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
