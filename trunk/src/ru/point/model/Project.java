package ru.point.model;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: Mikhail Sedov [12.01.2009]
 */
@Entity
@Table(name = "project_")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Project parent;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
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
}
