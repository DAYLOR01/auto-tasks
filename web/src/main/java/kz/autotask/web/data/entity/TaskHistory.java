package kz.autotask.web.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "task_histories", schema = "at")
public class TaskHistory {

    @Id
    private Long id;

    @ManyToOne
    private Task task;

    private String content;
    private Timestamp createdAt;

    @ManyToOne
    private User createdBy;

}
