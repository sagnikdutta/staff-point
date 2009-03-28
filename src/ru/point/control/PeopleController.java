package ru.point.control;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.point.dao.SmartDao;
import ru.point.model.*;
import ru.point.utils.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Mikhail Sedov [12.03.2009]
 */
@Controller
@Transactional
public class PeopleController {

    private static String LOGIN = "from User where login = ':login' and password = ':password'";

    private static String SEARCH_USERS = "from User where profile.firstName like ':query%' or " +
            "profile.secondName like ':query%' order by id";

    private static String ACTIVITY_REPORTS = "from Report where reportForActivity = :activity";

    private static String ACTIVITY_REPORTS_BY_DATE = "from Report where reportForActivity = ':activity' and " +
            "((reportPeriodStart between ':start' and ':end') or (reportPeriodEnd between ':start' and ':end'))";

    private static String PROJECT_REPORTS = "from Report where reportForActivity = ':activity' and " +
            "((reportPeriodStart between ':start' and ':end') or (reportPeriodEnd between ':start' and ':end'))";

    @Autowired
    private SmartDao dao;

    @RequestMapping("/")
    public ModelAndView index(@CookieValue(required = false) Cookie session,
                              ModelMap model) {
        model.put("projects", dao.findAll(Project.class));
        putCookie(session, model);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/error")
    public ModelAndView error(Exception ex, ModelMap model) {
        model.put("exception", ex);
        return new ModelAndView("error", model);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Sign up, Session

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(HttpServletResponse response,
                                  @RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  ModelMap map) {
        // check session
        Map<String, String> userPass = new HashMap<String, String>();
        userPass.put("login", login);
        userPass.put("password", Utils.md5(password));
        // User user = dao.findUniqueObject(User.class, LOGIN, userPass);
        User user = dao.findUniqueObject(User.class, "from User where login = '" + login +
                "' and password = '" + Utils.md5(password) + "'", userPass);
        if (user == null) {
            // user not found
            return error(new RuntimeException("login failed"), map);
        }
        // remove old user session
        dao.execute("delete from Session where user = " + user.getId());
        // generate new sesion and store it
        Session session = new Session();
        session.setUser(user);
        dao.save(session);

        Cookie cookie = new Cookie("session", session.getId());
        response.addCookie(cookie);

        return getUser(cookie, user.getId(), map);
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue(required = false) Cookie session) {
        if (session != null) {
            Session sessionBean = dao.get(Session.class, session.getValue());
            dao.delete(sessionBean);
        }

        return "redirect:/";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Users

    @RequestMapping("/user")
    public ModelAndView listUsers(@CookieValue(required = false) Cookie session, ModelMap model) {
        List<User> users = dao.findAll(User.class);
        for (User user : users) {
            Hibernate.initialize(user.getActivities());
        }
        model.put("users", users);
        putCookie(session, model);
        return new ModelAndView("users", model);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView filterUsers(@CookieValue(required = false) Cookie session, @RequestParam("query") String query, ModelMap model) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("query", query);
        List<User> users = dao.filter(User.class, SEARCH_USERS, args);
        for (User user : users) {
            Hibernate.initialize(user.getActivities());
        }
        model.put("users", users);
        putCookie(session, model);
        return new ModelAndView("users", model);
    }

    @RequestMapping("/user/{userId}")
    public ModelAndView getUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getActivities());
        for (Activity activity : user.getActivities()) {
            Hibernate.initialize(activity.getReportFrom());
        }
        Hibernate.initialize(user.getProfile().getContacts());
        model.put("user", user);
        // session
        putCookie(session, model);
        return new ModelAndView("user", model);
    }

    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.GET)
    public ModelAndView editUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getActivities());
        for (Activity activity : user.getActivities()) {
            Hibernate.initialize(activity.getReportFrom());
        }
        Hibernate.initialize(user.getProfile().getContacts());
        model.put("user", user);
        // session
        putCookie(session, model);
        return new ModelAndView("user-edit", model);
    }

    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.POST)
    public String updateUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        System.out.println("PeopleController.updateUser");
        return "redirect:/user/" + userId;
    }

    private void putCookie(Cookie sessionCookie, ModelMap model) {
        if (sessionCookie != null) {
            Session session = dao.get(Session.class, sessionCookie.getValue());
            if (session != null) {
                Hibernate.initialize(session.getUser());
                model.put("session", session);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Project

    @RequestMapping("/project")
    public ModelAndView listProjects(@CookieValue(required = false) Cookie session, ModelMap model) {
        putCookie(session, model);
        return new ModelAndView("projects", model);
    }

    @RequestMapping("/project/{projectId}")
    public ModelAndView getProject(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);
        putCookie(session, model);
        return new ModelAndView("project", model);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Reports

    @RequestMapping("/report/activity/{activityId}")
    public ModelAndView listActivityReport(@CookieValue(required = false) Cookie session,
                                           @PathVariable long activityId,
                                           ModelMap model) {
        model.put("reports", dao.getReportsForActivityId(activityId));
        model.put("activity", dao.get(Activity.class, activityId));
        model.put("title", "&rarr; Activity");
        putCookie(session, model);
        return new ModelAndView("reports", model);
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
        Map<String, String> args = new HashMap<String, String>();
        args.put("activity", String.valueOf(activityId));
        args.put("start", Utils.formatCalendar(start));
        args.put("end", Utils.formatCalendar(end));

        model.addAttribute("reports", dao.filter(Report.class, ACTIVITY_REPORTS_BY_DATE, args));
        putCookie(session, model);
        return new ModelAndView("reports", model);
    }

    @RequestMapping("/report/project/{projectId}/year/{year}/week/{week}")
    public ModelAndView listProjectReports(@CookieValue(required = false) Cookie session,
                                           @PathVariable long projectId,
                                           @PathVariable int year,
                                           @PathVariable int weekNo,
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

        Map<String, String> args = new HashMap<String, String>();
        args.put("project", String.valueOf(projectId));
        args.put("start", Utils.formatCalendar(start));
        args.put("end", Utils.formatCalendar(end));

        model.addAttribute("reports", dao.filter(Report.class, PROJECT_REPORTS, args));
        putCookie(session, model);
        return new ModelAndView("reports", model);

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
        report.setReportPeriodStart(start);
        report.setReportPeriodEnd(end);
        report.setText(reportText);
        report.setReportForActivity(dao.get(Activity.class, activityId));

        dao.save(report);

        return "redirect:/report/activity/" + activityId;
    }

    @RequestMapping(value = "/report/{reportId}", method = RequestMethod.DELETE)
    public String deleteReport(@CookieValue(required = false) Cookie session,
                               @PathVariable long reportId) {
        dao.delete(Report.class, reportId);
        return "redirect:/";
    }
}
