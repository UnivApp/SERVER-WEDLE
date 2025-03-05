package yerong.wedle.todo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.todo.domain.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Optional<TodoList> findByMember(Member member);
}
