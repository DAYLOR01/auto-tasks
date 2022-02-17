package kz.autotask.web.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "external_apps", schema = "at")
public class ExternalApp {

    @Id
    private Long id;

    private String token;
    private String name;

    @ManyToMany
    private List<Tag> tags;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
