package ru.point.view;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Mikhail Sedov {02.04.2009}
 */
public class Group<Bean> {

    private String name;
    private Collection<Bean> elements;

    public Group() {
        elements = new LinkedList<Bean>();
    }

    public Group(String name, Collection<Bean> elements) {
        this.name = name;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Bean> getElements() {
        return elements;
    }

    public void setElements(Collection<Bean> elements) {
        this.elements = elements;
    }

}
