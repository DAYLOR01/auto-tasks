package kz.autotask.web.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "topics", schema = "at")
public class Topic {

    @Id
    @SequenceGenerator(name="topics_id_seq",
            sequenceName="topics_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="topics_id_seq")
    private Long id;

    @ManyToOne
    @JoinTable(name = "author_id")
    private User author;

    private String header;
    private String poster;
    private String content;

    public Long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
