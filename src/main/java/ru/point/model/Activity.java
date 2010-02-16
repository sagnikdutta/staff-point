package ru.point.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.*;

/**
 * @author Mikhail Sedov [12.01.2009]
 */
@Entity
public class Activity {

    @PrimaryKey(sequence = "id")
    private long id;

    private String name;

    private Role role;

    private User user;

    private Project project;

    private Set<Activity> reportFrom = new TreeSet<Activity>();

    private Activity reportTo;

    private Calendar start;

    private Calendar end;

    private boolean isMain;

    private List<Report> reports = new LinkedList<Report>();

    public Activity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "?" : name;
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

    public Report getLastReport() {
        return reports.isEmpty() ? null : reports.get(0);
    }

    @Override
    public String toString() {
        return role.getName() + "#" + id;
    }
}
