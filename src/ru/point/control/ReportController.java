package ru.point.control;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;
import ru.point.model.Activity;
import ru.point.model.Report;
import ru.point.model.User;
import ru.point.utils.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author: Mikhail Sedov [25.03.2009]
 */
@Controller
@Transactional
public class ReportController extends AbstractController {

    private static String ACTIVITY_REPORTS = "from Report where reportForActivity.user.id = :user";

    private static String ACTIVITY_REPORTS_BY_DATE = "from Report where reportForActivity = ':activity' and " +
            "((reportPeriodStart between ':start' and ':end') or (reportPeriodEnd between ':start' and ':end'))";

    private static String PROJECT_REPORTS = "from Report where reportForActivity = ':activity' and " +
            "((reportPeriodStart between ':start' and ':end') or (reportPeriodEnd between ':start' and ':end'))";


    @RequestMapping("/user/report/{userId}")
    public ModelAndView listActivityReport(@CookieValue(required = false) Cookie session,
                                           @PathVariable long userId,
                                           ModelMap model) {

        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getMainActivity());
        model.put("user", user);

        model.put("reports", dao.listReportsForUser(userId));
        
        putCookie(session, model);
        return new ModelAndView("user-reports", model);
    }

    @RequestMapping("/report/activity/{activityId}/year/{year}/week/{week}")
    public ModelAndView listActivityReport(@CookieValue(required = false) Cookie session,
                                           @PathVariable long activityId,
                                           @PathVariable int year,
                                           @PathVariable int weekNo,
                                           ModelMap model) {
        Calendar start = Utils.getStartOfWeek(year, weekNo);
        Calendar end = Utils.getEndOfWeek(start);
        // return it
        return listActivityReport(session, activityId, start, end, model);
    }

    @RequestMapping("/report/activity/{activityId}/start/{start}/end/{end}")
    public ModelAndView listActivityReport(@CookieValue(required = false) Cookie session,
                                           @PathVariable long activityId,
                                           @PathVariable Calendar start,
                                           @PathVariable Calendar end,
                                           ModelMap model) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("activity", String.valueOf(activityId));
        args.put("start", Utils.formatCalendar(start));
        args.put("end", Utils.formatCalendar(end));

        model.addAttribute("reports", dao.filter(Report.class, ACTIVITY_REPORTS_BY_DATE, args));
        putCookie(session, model);
        return new ModelAndView("user-reports", model);
    }

    @RequestMapping("/report/project/{projectId}/year/{year}/week/{week}")
    public ModelAndView listProjectReports(@CookieValue(required = false) Cookie session,
                                           @PathVariable("projectId") long projectId,
                                           @PathVariable("year") int year,
                                           @PathVariable("weekNo") int weekNo,
                                           ModelMap model) {
        Calendar start = Utils.getStartOfWeek(year, weekNo);
        Calendar end = Utils.getEndOfWeek(start);
        // return it
        return listProjectReports(session, projectId, start, end, model);
    }

    @RequestMapping("/report/project/{projectId}/start/{start}/end/{end}")
    public ModelAndView listProjectReports(@CookieValue(required = false) Cookie session,
                                           @PathVariable long projectId,
                                           @PathVariable Calendar start,
                                           @PathVariable Calendar end,
                                           ModelMap model) {

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("project", String.valueOf(projectId));
        args.put("start", Utils.formatCalendar(start));
        args.put("end", Utils.formatCalendar(end));

        model.addAttribute("reports", dao.filter(Report.class, PROJECT_REPORTS, args));
        putCookie(session, model);
        return new ModelAndView("user-reports", model);

    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("'d'yyyyMMdd");

    @RequestMapping(value = "/report/activity/{activityId}", method = RequestMethod.POST)
    public String postActivityReport(@CookieValue(required = false) Cookie session,
                                     @PathVariable long activityId,
                                     HttpServletRequest request) throws UnsupportedEncodingException, ParseException {

        request.setCharacterEncoding("UTF8");

        Map<String, String[]> map = request.getParameterMap();
        String text = map.get("text")[0];
        String[] days = map.get("selected");

        if (text != null && text.length() > 0 && days.length > 0) {
            Report r = new Report();
            r.setText(text);
            r.setReportForActivity(dao.get(Activity.class, activityId));
            for (String day : days) {
                Calendar dayCal = Calendar.getInstance();
                dayCal.setTime(DATE_FORMAT.parse(day));
                r.addReportPeriodDays(dayCal);
            }
            dao.save(r);

            return String.valueOf(r.getId());
        }

        return null;
    }

    @RequestMapping(value = "/report/activity/{activityId}/year/{year}/week/{weekNo}", method = RequestMethod.POST)
    public String postActivityReport(@CookieValue(required = false) Cookie session,
                                     @PathVariable long activityId,
                                     @PathVariable int year,
                                     @PathVariable int weekNo,
                                     @RequestParam String reportText) {
        Calendar start = Utils.getStartOfWeek(year, weekNo);
        Calendar end = Utils.getEndOfWeek(start);
        // save it
        return postActivityReport(session, activityId, start, end, reportText);
    }

    @RequestMapping(value = "/report/activity/{activityId}/start/{start}/end/{end}", method = RequestMethod.POST)
    public String postActivityReport(@CookieValue(required = false) Cookie session,
                                     @PathVariable long activityId,
                                     @PathVariable Calendar start,
                                     @PathVariable Calendar end,
                                     @RequestParam String reportText) {

        Report report = new Report();
        report.setText(reportText);
        report.setReportForActivity(dao.get(Activity.class, activityId));

        dao.save(report);

        return "redirect:/report/activity/" + activityId;
    }

    @RequestMapping(value = "/report/{reportId}", method = RequestMethod.DELETE)
    public String deleteReport(@CookieValue(required = false) Cookie session,
                               @PathVariable("reportId") long reportId) {
        dao.delete(Report.class, reportId);
        return "redirect:/";
    }
}