package ru.point.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "session_")
public class Session {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @ManyToOne
    private User user;

    @Column(name = "loginUnixTime")
    private long loginUnixTime;

    public Session() {
        loginUnixTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLoginUnixTime() {
        return loginUnixTime;
    }

    @Override
    public String toString() {
        return "Session: " + id + " | " + user.getFullName() + " logged at " + new Date(loginUnixTime);
    }
}
