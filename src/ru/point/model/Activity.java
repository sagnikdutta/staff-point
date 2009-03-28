package ru.point.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: Mikhail Sedov [12.01.2009]
 */
@Entity
@Table(name = "activity_")
public class Activity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "reportTo", fetch = FetchType.LAZY)
    private Set<Activity> reportFrom = new TreeSet<Activity>();

    @ManyToOne
    private Activity reportTo;

    @Column(name = "startDate")
    private Calendar start;

    @Column(name = "endDate")
    private Calendar end;

    @Column(name = "isMain")
    private boolean isMain;

    public Activity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getReportTo() {
        return reportTo;
    }

    public void setReportTo(Activity reportTo) {
        this.reportTo = reportTo;
    }

    public Set<Activity> getReportFrom() {
        return reportFrom;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public boolean isActive() {
        return end == null;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public String toString() {
        return name;
    }
}
