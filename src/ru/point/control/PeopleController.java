package ru.point.control;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.point.model.*;
import ru.point.utils.Utils;
import ru.point.view.Group;
import ru.point.view.Message;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Mikhail Sedov [12.03.2009]
 */
@Controller
@Transactional
public class PeopleController extends AbstractController {

    private static String LOGIN = "from User where login = ':login' and password = ':password'";

    private static String SEARCH_USERS = "from User where profile.firstName like ':query%' or " +
            "profile.secondName like ':query%' order by id";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Sign up, Session

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(HttpServletResponse response,
                                  @RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  ModelMap map) {
        // check session
        Map<String, Object> userPass = new HashMap<String, Object>();
//        userPass.put("login", login);
//        userPass.put("password", Utils.md5(password));
//        User user = dao.findUniqueObject(User.class, LOGIN, userPass);
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
        Map<String, Object> args = new HashMap<String, Object>();
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
        Hibernate.initialize(user.getMainActivity());
        Hibernate.initialize(user.getProfile().getContacts());
        Hibernate.initialize(user.getProfile().getSocial());
        model.put("user", user);
        // session
        putCookie(session, model);
        return new ModelAndView("user-info", model);
    }

    @RequestMapping("/user/team/{userId}")
    public ModelAndView getUserTeam(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getActivities());
        for (Activity activity : user.getActivities()) {
            Hibernate.initialize(activity.getReportFrom());
        }
        model.put("user", user);
        // session
        putCookie(session, model);
        return new ModelAndView("user-team", model);
    }

    @RequestMapping("/user/edit/{userId}")
    public ModelAndView editUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, Message message, ModelMap model) {

        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getMainActivity());

        if (!isAllowedForCurrentUser(session, user)) {
            return new ModelAndView(new RedirectView("/user/" + userId));
        }

        model.put("user", user);
        model.put("contactKeys", merge(Profile.CONTACT_KEYS, user.getProfile().getContacts().keySet()));
        model.put("socialKeys", merge(Profile.SOCIAL_KEYS, user.getProfile().getSocial().keySet()));
        model.put("message", message);
        // session
        putCookie(session, model);
        return new ModelAndView("user-edit", model);
    }

    private String[] merge(String[] one, Collection<String> two) {
        Set<String> result = new TreeSet<String>();
        result.addAll(Arrays.asList(one));
        result.addAll(two);
        return result.toArray(new String[result.size()]);
    }

    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.POST)
    public ModelAndView saveUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId,
                                 HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        // russian
        request.setCharacterEncoding("CP1251");
        User user = dao.get(User.class, userId);
        // 
        Map<String, String[]> m = request.getParameterMap();
        for (String key : m.keySet()) {
            String value = m.get(key)[0];
            if (key.equals("firstName")) {
                user.getProfile().setFirstName(value);
            } else if (key.equals("secondName")) {
                user.getProfile().setSecondName(value);
            } else if (key.equals("sex")) {
                user.setFemale(Boolean.parseBoolean(value));
            } else if (key.equals("birthday")) {
                try {
                    Calendar birthday = Calendar.getInstance();
                    birthday.setTime(new SimpleDateFormat("dd.MM.yy").parse(value));
                    user.getProfile().setBirthDay(birthday);
                } catch (ParseException e) {
                    // todo: message
                }
            } else if (key.startsWith("contacts.")) {
                if (value.length() > 0) {
                    user.getProfile().getContacts().put(key.substring("contacts.".length()), value);
                } else {
                    user.getProfile().getContacts().remove(key.substring("contacts.".length()));
                }
            } else if (key.startsWith("social.")) {
                if (value.length() > 0) {
                    user.getProfile().getSocial().put(key.substring("social.".length()), value);
                } else {
                    user.getProfile().getSocial().remove(key.substring("social.".length()));
                }
            }
        }

        dao.save(user);

        return editUser(session, userId, new Message("���������� ���������� ���������"), model);
    }

    @RequestMapping(value = "/user/edit/password/{userId}", method = RequestMethod.POST)
    public ModelAndView saveUserPassword(@CookieValue(required = false) Cookie session,
                                         @PathVariable("userId") long userId,
                                         @RequestParam("old") String old,
                                         @RequestParam("new") String newPassword,
                                         @RequestParam("again") String newPasswordAgain,
                                         ModelMap model) throws UnsupportedEncodingException {

        User user = dao.get(User.class, userId);

        if (!user.getPassword().equals(Utils.md5(old))) {
            return editUser(session, userId, new Message(Message.PASSWORD_OLD_WRONG, false), model);
        }

        if (!newPassword.equals(newPasswordAgain)) {
            return editUser(session, userId, new Message(Message.PASSWORD_NOT_MATCH, false), model);
        }

        if (newPassword.length() < 4) {
            return editUser(session, userId, new Message(Message.PASSWORD_TOO_SHORT, false), model);
        }


        user.setPassword(Utils.md5(newPassword));
        dao.save(user);

        return editUser(session, userId, new Message(Message.PASSWORD_UPDATED), model);
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

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();
        groups.add(new Group<Activity>("", project.getActivities()));

        model.put("groups", groups);
        putCookie(session, model);
        return new ModelAndView("project", model);
    }

    @RequestMapping("/project/{projectId}/by/role")
    public ModelAndView getProjectByRole(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        Set<String> roles = new HashSet<String>();
        for (Activity activity : project.getActivities()) {
            roles.add(activity.getRole().getName());
        }

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();

        for (String role : roles) {
            List<Activity> activities = null;
            for (Activity activity : project.getActivities()) {
                if (activity.getRole().getName().equals(role)) {
                    if (activities == null) {
                        activities = new LinkedList<Activity>();
                    }
                    activities.add(activity);
                }
            }
            if (activities != null) {
                groups.add(new Group<Activity>(role, activities));
            }
        }

        model.put("groups", groups);
        putCookie(session, model);
        return new ModelAndView("project", model);
    }

    @RequestMapping("/project/{projectId}/by/name")
    public ModelAndView getProjectByName(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();

        for (String first : new String[]{"�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",}) {
            List<Activity> activities = null;
            for (Activity activity : project.getActivities()) {
                if (activity.getUser().getProfile().getSecondName().startsWith(first)) {
                    if (activities == null) {
                        activities = new LinkedList<Activity>();
                    }
                    activities.add(activity);
                }
            }
            if (activities != null) {
                groups.add(new Group<Activity>(first, activities));
            }
        }

        model.put("groups", groups);
        putCookie(session, model);
        return new ModelAndView("project", model);
    }

    @RequestMapping("/project/{projectId}/by/birthday")
    public ModelAndView getProjectByBirthday(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();
        groups.add(new Group<Activity>("", dao.getProjectActivitiesByBirthday(projectId)));

        model.put("groups", groups);
        model.put("groupby", "birthday");
        putCookie(session, model);
        return new ModelAndView("project", model);
    }
}
