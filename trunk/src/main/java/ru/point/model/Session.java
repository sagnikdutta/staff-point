package ru.point.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Date;

/**
 * @author Mikhail Sedov [06.03.2009]
 */
@Entity
public class Session {

    @PrimaryKey
    private String id;

    private User user;

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

    public boolean isAdmin() {
        return true;
    }

    public void setAdmin(boolean admin) {
        // isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Session: " + id + " | " + user.getFullName() + " logged at " + new Date(loginUnixTime);
    }
}
