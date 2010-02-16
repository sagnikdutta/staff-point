package ru.point.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;


/**
 * @author Mikhail Sedov [25.03.2009]
 */
@Entity
public class Role {

    public static int DESIGNER_POLICY = 1;
    public static int MANAGER_POLICY = 2;
    public static int SEO_POLICY = 3;
    public static int HR_POLICY = 4;

    @PrimaryKey(sequence = "id")
    private long id;

    private String name;

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
