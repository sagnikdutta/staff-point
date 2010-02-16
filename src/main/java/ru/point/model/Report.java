package ru.point.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * @author Mikhail Sedov [06.03.2009]
 */
@Entity
public class Report {

    @PrimaryKey(sequence = "id")
    private long id;

    private Activity reportForActivity;

    private String text;

    private Collection<Calendar> reportPeriodDays = new TreeSet<Calendar>();

    private Calendar start;

    private Calendar end;

    private long diff = Long.MIN_VALUE;

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

    public boolean isOld() {
        checkDiff();
        return diff > 5;
    }

    public String getPassed() {
        checkDiff();
        if (diff <= 0) {
            return "свежак";
        } else if (diff % 10 == 1) {
            return diff + " день назад";
        } else if (diff % 10 < 5 && diff % 10 != 0) {
            return diff + " дня назад";
        } else {
            return diff + " дней назад";
        }
    }

    private void checkDiff() {
        if (diff == Long.MIN_VALUE) {
            diff = (new Date().getTime() - end.getTime().getTime()) / 1000 / 60 / 60 / 24;
        }
    }
}
