package kz.autotask.web.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "topics", schema = "at")
public class Topic {

    @Id
    @SequenceGenerator(name="topics_id_seq",
            sequenceName="topics_id_seq",
            allocationSize=1,
            schema = "at")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="topics_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String header;
    private String content;
}
