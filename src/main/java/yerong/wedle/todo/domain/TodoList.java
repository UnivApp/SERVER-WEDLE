package yerong.wedle.todo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;
import yerong.wedle.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public TodoList(Member member) {
        this.member = member;
    }

    public boolean isAllTasksCompleted() {
        return todos.stream().allMatch(Todo::isCompleted);
    }

    public void addTask(Todo todo) {
        todo.addTodoList(this);
        this.todos.add(todo);
    }
}
