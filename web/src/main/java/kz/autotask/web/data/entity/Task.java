package kz.autotask.web.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks", schema = "at")
public class Task {

    @Id
    @SequenceGenerator(name="tasks_id_seq",
            sequenceName="tasks_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="tasks_id_seq")
    private Long id;

    private String header;
    private String text;
    private Date assignDate;
    private Date inspirationDate;
    private Timestamp spentTime;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private User authorUser;

    private Boolean createdByExtApp;

    @ManyToOne
    @JoinColumn(name = "author_ext_app_id")
    private ExternalApp authorExternalApp;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(mappedBy = "task")
    private List<TaskHistory> history;

    public Long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getInspirationDate() {
        return inspirationDate;
    }

    public void setInspirationDate(Date inspirationDate) {
        this.inspirationDate = inspirationDate;
    }

    public Timestamp getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Timestamp spentTime) {
        this.spentTime = spentTime;
    }

    public boolean isCreatedByExtApp() {
        return createdByExtApp;
    }

    public void setCreatedByExtApp(boolean createdByExtApp) {
        this.createdByExtApp = createdByExtApp;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public User getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(User authorUser) {
        this.authorUser = authorUser;
    }

    public ExternalApp getAuthorExternalApp() {
        return authorExternalApp;
    }

    public void setAuthorExternalApp(ExternalApp authorExternalApp) {
        this.authorExternalApp = authorExternalApp;
    }

    public List<TaskHistory> getHistory() {
        return history;
    }
}
