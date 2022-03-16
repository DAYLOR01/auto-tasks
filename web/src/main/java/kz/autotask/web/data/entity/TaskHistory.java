package kz.autotask.web.data.entity;

import kz.autotask.web.data.entity.enums.TaskHistoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
@Table(name = "task_histories", schema = "at")
public class TaskHistory {

    @Id
    @SequenceGenerator(name="task_histories_id_seq",
            sequenceName="task_histories_id_seq",
            allocationSize=1,
            schema = "at")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="task_histories_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private TaskHistoryType type;

    private String value;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
