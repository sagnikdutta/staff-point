package ru.point.model;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mikhail Sedov [12.01.2009]
 */
@Embeddable
public class Profile {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;

    @Column(name = "birthDay")
    private Calendar birthDay;

    @CollectionOfElements(fetch = FetchType.LAZY)
    private Map<String, String> contacts = new HashMap<String, String>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public Calendar getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Calendar birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return firstName + " " + secondName;
    }
}
