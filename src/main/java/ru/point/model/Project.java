package ru.point.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Mikhail Sedov [12.01.2009]
 */
@Entity
public class Project {

    @PrimaryKey(sequence = "id")
    private long id;

    private String name;

    private Project parent;

    private Set<Project> children = new TreeSet<Project>();

    private Set<Activity> activities = new TreeSet<Activity>();

    public Project() {
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

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Set<Project> getChildren() {
        return children;
    }
}
