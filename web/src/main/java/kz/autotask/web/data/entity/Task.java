package kz.autotask.web.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tasks", schema = "at")
public class Task {

    @Id
    private Long id;

    private String header;
    private String text;
    private Date assignDate;
    private Date inspirationDate;
    private Timestamp spentTime;

    @ManyToOne
    private User assignedUser;

    @ManyToOne
    private User authorUser;

    private boolean createdByExtApp;

    @ManyToOne
    private ExternalApp authorExternalApp;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public void setId(Long id) {
        this.id = id;
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
}
