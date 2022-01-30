package kz.autotask.web.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles", schema = "at")
public class Role {

    @Id
    private Integer id;

    private String name;
    private int userPriority;



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserPriority() {
        return userPriority;
    }

    public void setUserPriority(int userPriority) {
        this.userPriority = userPriority;
    }
}
