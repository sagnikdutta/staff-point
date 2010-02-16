package ru.point.model.board;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "thread")
public class Topic {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "topicStart")
    private Calendar topicStartTime;

    @Column(name = "lastActivity")
    private Calendar lastActivity;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Option> options;

    public Topic() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getTopicStartTime() {
        return topicStartTime;
    }

    public void setTopicStartTime(Calendar topicStartTime) {
        this.topicStartTime = topicStartTime;
    }

    public Calendar getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Calendar lastActivity) {
        this.lastActivity = lastActivity;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
