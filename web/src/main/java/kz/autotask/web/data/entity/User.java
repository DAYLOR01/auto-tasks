package kz.autotask.web.data.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", schema = "at")
public class User {

    @Id
    private Long id;

    private String username;
    private String password;
    private String name;
    private boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            schema = "at",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
