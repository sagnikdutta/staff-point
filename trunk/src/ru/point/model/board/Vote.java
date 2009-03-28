package ru.point.model.board;

import ru.point.model.User;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "vote")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Vote {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToOne
    private Option option;

    @OneToOne
    private Topic topic;

    @OneToOne
    private User user;

    @Column(name = "time")
    private Calendar time;

    @Column(name = "updateTime")
    private Calendar updateTime;

    public Vote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
