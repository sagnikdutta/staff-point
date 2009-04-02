package ru.point.model;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne
    private Role role;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "reportTo", fetch = FetchType.LAZY)
    @OrderBy("id")
    private Set<Activity> reportFrom = new TreeSet<Activity>();

    @ManyToOne
    private Activity reportTo;

    @Column(name = "startDate")
    private Calendar start;

    @Column(name = "endDate")
    private Calendar end;

    @Column(name = "isMain")
    private boolean isMain;

    @OneToMany(mappedBy = "reportForActivity", fetch = FetchType.LAZY)
    @OrderBy("end desc")
    private List<Report> reports = new LinkedList<Report>();

    public Activity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public List<Report> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        return role.getName() + "#" + id;
    }
}
