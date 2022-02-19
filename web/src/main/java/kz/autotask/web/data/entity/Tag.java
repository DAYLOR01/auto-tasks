package kz.autotask.web.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "tags", schema = "at")
public class Tag {

    @Id
    @SequenceGenerator(name="tags_id_seq",
            sequenceName="tags_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="tags_id_seq")
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagUsability usability;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagUsability getUsability() {
        return usability;
    }

    public void setUsability(TagUsability usability) {
        this.usability = usability;
    }
}
