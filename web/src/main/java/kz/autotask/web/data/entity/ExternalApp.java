package kz.autotask.web.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "external_apps", schema = "at")
public class ExternalApp {

    @Id
    @SequenceGenerator(name="external_apps_id_seq",
            sequenceName="external_apps_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="external_apps_id_seq")
    private Long id;

    private String apiKey;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "external_apps_tags",
            joinColumns = @JoinColumn(name = "ext_app_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public Long getId() {
        return id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
