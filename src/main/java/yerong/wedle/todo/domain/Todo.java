package yerong.wedle.todo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yerong.wedle.common.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private boolean isCompleted = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PRIVATE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    public Todo(LocalDate date, String task, TodoList todoList, Visibility visibility) {
        this.date = date;
        this.task = task;
        this.isCompleted = false;
        this.todoList = todoList;
        this.visibility = visibility;
    }

    public void addTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public void updateTask(String task) {
        this.task = task;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public void updateCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void updateVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
