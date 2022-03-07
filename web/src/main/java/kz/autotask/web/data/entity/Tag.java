package kz.autotask.web.data.entity;

import kz.autotask.web.data.entity.enums.TagUsability;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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
    private String descriptionRu;

    @Enumerated(EnumType.STRING)
    private TagUsability usability;
}
