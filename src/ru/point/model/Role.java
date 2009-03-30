package ru.point.model;

import javax.persistence.*;

/**
 * @author: Mikhail Sedov [25.03.2009]
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "groupPolicy")
    private GroupPolicy groupPolicy;

    public Role() {
    }

    public Role(long id) {
        this.id = id;
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

    public GroupPolicy getGroupPolicy() {
        return groupPolicy;
    }

    public void setGroupPolicy(GroupPolicy groupPolicy) {
        this.groupPolicy = groupPolicy;
    }
}
