package ru.point.model;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.Collection;
import java.util.Calendar;
import java.util.TreeSet;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "report_")
public class Report {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    private Activity reportForActivity;

    @Column(name = "text")
    private String text;

    @CollectionOfElements(fetch = FetchType.LAZY)
    private Collection<Calendar> reportPeriodDays = new TreeSet<Calendar>();

    @Column(name = "cachedStart")
    private Calendar start;

    @Column(name = "cachedEnd")
    private Calendar end;

    public Report() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Activity getReportForActivity() {
        return reportForActivity;
    }

    public void setReportForActivity(Activity reportForPosition) {
        this.reportForActivity = reportForPosition;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<Calendar> getReportPeriodDays() {
        return reportPeriodDays;
    }

    public void addReportPeriodDays(Calendar dayCal) {
        reportPeriodDays.add(dayCal);
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
}
