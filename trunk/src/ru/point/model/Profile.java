package ru.point.model;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * @author: Mikhail Sedov [12.01.2009]
 */
@Embeddable
public class Profile {

    public static final String[] CONTACT_KEYS = {
            "Мобильный телефон",
            "Домашний телефон",
            "e-mail",
            "Skype",
            "ICQ",
            "GTalk",
    };

    public static final String[] SOCIAL_KEYS = {
            "ВКонтакте",
            "LinkedIn",
            "Last.fm",
            "FriendFeed",
    };

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;

    @Column(name = "birthDay")
    private Calendar birthDay;

    @Column(name = "facePath")
    private String facePath;

    @CollectionOfElements(fetch = FetchType.LAZY)
    private Map<String, String> contacts = new HashMap<String, String>();

    @CollectionOfElements(fetch = FetchType.LAZY)
    private Map<String, String> social = new HashMap<String, String>();

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

    public Map<String, String> getSocial() {
        return social;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public String getDaysTillBirthday() {

        Date today = new Date();

        Calendar nextBirthday = Calendar.getInstance();
        nextBirthday.set(Calendar.DATE, birthDay.get(Calendar.DATE));
        nextBirthday.set(Calendar.MONTH, birthDay.get(Calendar.MONTH));
        if (today.compareTo(nextBirthday.getTime()) >= 0) {
            nextBirthday.add(Calendar.YEAR, 1);
        }
        return String.valueOf((nextBirthday.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60 / 60 / 24);
    }

    @Override
    public String toString() {
        return firstName + " " + secondName;
    }
}
