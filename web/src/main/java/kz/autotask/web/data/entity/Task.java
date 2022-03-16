package kz.autotask.web.data.entity;

import kz.autotask.web.data.entity.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "tasks", schema = "at")
public class Task {

    @Id
    @SequenceGenerator(name="tasks_id_seq",
            sequenceName="tasks_id_seq",
            allocationSize=1,
            schema = "at")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="tasks_id_seq")
    private Long id;

    private String header;
    private String text;
    private LocalDate assignDate;
    private LocalDate inspirationDate;
    private LocalDate completionDate;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private User authorUser;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(mappedBy = "task")
    private List<TaskHistory> history;

    @ManyToMany
    @JoinTable(
            name = "tasks_tags",
            schema = "at",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
