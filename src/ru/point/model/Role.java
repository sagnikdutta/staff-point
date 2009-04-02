package ru.point.model;

import javax.persistence.*;

/**
 * @author: Mikhail Sedov [25.03.2009]
 */
@Entity
@Table(name = "role_")
public class Role {

    public static int DESIGNER_POLICY = 1;
    public static int MANAGER_POLICY = 2;
    public static int SEO_POLICY = 3;
    public static int HR_POLICY = 4;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "groupPolicy")
    private int groupPolicy;

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

    public int getGroupPolicy() {
        return groupPolicy;
    }

    public void setGroupPolicy(int groupPolicy) {
        this.groupPolicy = groupPolicy;
    }
}
