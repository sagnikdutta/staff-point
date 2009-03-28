package ru.point.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: Mikhail Sedov [09.01.2009]
 */
@Entity
@Table(name = "user_", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"})
})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Embedded
    private Profile profile;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Activity> activities = new TreeSet<Activity>();

    @Column(name = "hireDay")
    private Calendar hireDay;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getFullName() {
        return profile.getFirstName() + " " + profile.getSecondName();
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getHireDay() {
        return hireDay;
    }

    public void setHireDay(Calendar hireDay) {
        this.hireDay = hireDay;
    }

    public Activity getMainActivity() {
        for (Activity activity : activities) {
            if (activity.isMain()) {
                return activity;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "User: " + id + " / " + profile + " / " + activities;
    }
}
