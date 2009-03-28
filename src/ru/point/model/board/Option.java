package ru.point.model.board;

import javax.persistence.*;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    private Topic topic;

    @Column(name = "description")
    private String description;

    @Column(name = "hint")
    private String hint;

    public Option() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
