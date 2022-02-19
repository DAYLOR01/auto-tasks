package kz.autotask.web.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_histories", schema = "at")
public class TaskHistory {

    @Id
    @SequenceGenerator(name="task_histories_id_seq",
            sequenceName="task_histories_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="task_histories_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String content;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
