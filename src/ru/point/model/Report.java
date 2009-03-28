package ru.point.model;

import ru.point.utils.Utils;

import javax.persistence.*;
import java.util.Calendar;

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

    @Column(name = "reportPeriodStart")
    private Calendar reportPeriodStart;

    @Column(name = "reportPeriodEnd")
    private Calendar reportPeriodEnd;

    @Column(name = "text")
    private String text;

    public Report() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Activity getReportForPosition() {
        return reportForActivity;
    }

    public void setReportForActivity(Activity reportForActivity) {
        this.reportForActivity = reportForActivity;
    }

    public Calendar getReportPeriodStart() {
        return reportPeriodStart;
    }

    public void setReportPeriodStart(Calendar reportPeriodStart) {
        this.reportPeriodStart = reportPeriodStart;
    }

    public Calendar getReportPeriodEnd() {
        return reportPeriodEnd;
    }

    public void setReportPeriodEnd(Calendar reportPeriodEnd) {
        this.reportPeriodEnd = reportPeriodEnd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return Utils.formatCalendar(reportPeriodStart) + " - " + Utils.formatCalendar(reportPeriodEnd) + " > " + getText();
    }
}
