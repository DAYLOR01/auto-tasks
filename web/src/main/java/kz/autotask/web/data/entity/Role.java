package kz.autotask.web.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "roles", schema = "at")
public class Role {

    @Id
    @SequenceGenerator(name="roles_id_seq",
            sequenceName="roles_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="roles_id_seq")
    private Integer id;

    private String name;
    private int userPriority;
}
