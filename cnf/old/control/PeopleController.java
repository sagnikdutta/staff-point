package ru.point.control;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.point.model.Activity;
import ru.point.model.Profile;
import ru.point.model.Session;
import ru.point.model.User;
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
 * @author Mikhail Sedov [12.03.2009]
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
    public String loginUser(HttpServletResponse response,
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
            return "redirect:/";
        }
        // remove old user session
        // dao.execute("delete from Session where user = " + user.getId());
        // generate new sesion and store it
        Session session = new Session();
        session.setUser(user);
        dao.save(session);

        Cookie cookie = new Cookie("session", session.getId());
        response.addCookie(cookie);

        if (user.getProfile().getFirstName() == null || user.getProfile().getSecondName() == null) {
            editUser(cookie, user.getId(), null, map);
        }

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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String listUsers(@CookieValue(required = false) Cookie session, ModelMap model) {

        Group<User> group = new Group<User>();

        List<User> users = dao.findAll(User.class);

        for (User user : users) {
            Hibernate.initialize(user.getActivities());
            group.getElements().add(user);
        }

        model.put("groups", Arrays.asList(group));
        model.put("amount", users.size());
        putCookie(session, model);

        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String registerUser(HttpServletResponse response,
                               @CookieValue(required = false) Cookie session,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("again") String again,
                               ModelMap model) {

        User user = dao.findUniqueObject(User.class, "from User where login = '" + email + "'");
        if (user != null) {
            return signup(session, email, new Message("������������ � ����� e-mail ��� ���������������", false), model);
        }

        if (!password.equals(again)) {
            return signup(session, email, new Message("������ �� ���������", false), model);
        }

        user = new User();
        user.setLogin(email);
        user.setPassword(Utils.md5(password));

        user.setProfile(new Profile());
        user.getProfile().getContacts().put("e-mail", email);

        dao.save(user);

        return loginUser(response, email, password, model);
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String signup(@CookieValue(required = false) Cookie session, String email, Message message, ModelMap model) {
        model.put("message", message);
        model.put("email", email);
        putCookie(session, model);
        return "welcome";
    }

    @RequestMapping("/user/by/role")
    public String filterUsersByName(ModelMap model) throws UnsupportedEncodingException {

        List<User> users = dao.findAll(User.class);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();

        Set<String> roles = new HashSet<String>();
        for (User user : users) {
            roles.add(user.getMainActivity().getRole().getName());
        }

        for (String role : roles) {
            List<Activity> activities = null;
            for (User user : users) {
                Activity activity = user.getMainActivity();
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
        model.put("amount", users.size());

        return "users";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String filterUsers(@CookieValue(required = false) Cookie session,
                              // @RequestParam("query") String query,
                              HttpServletRequest request,
                              ModelMap model) throws UnsupportedEncodingException {

        request.setCharacterEncoding("CP1251");
        String query = request.getParameter("query");

        // List<User> users = dao.filter(User.class, SEARCH_USERS, args);
        List<User> users = dao.filter(User.class, "from User where profile.firstName like '" + query + "%' or " +
                "profile.secondName like '" + query + "%' order by id");

        for (User user : users) {
            Hibernate.initialize(user.getActivities());
        }

        List<Group<User>> groups = new LinkedList<Group<User>>();
        groups.add(new Group<User>("", users));

        model.put("groups", groups);
        model.put("amount", users.size());

        return "users";
    }

    @RequestMapping("/user/{userId}")
    public String getUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getMainActivity());
        Hibernate.initialize(user.getProfile().getContacts());
        Hibernate.initialize(user.getProfile().getSocial());
        model.put("user", user);
        // session
        putCookie(session, model);

        return "user-info";
    }

    @RequestMapping("/user/team/{userId}")
    public String getUserTeam(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, ModelMap model) {
        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getActivities());
        for (Activity activity : user.getActivities()) {
            Hibernate.initialize(activity.getReportFrom());
        }
        model.put("user", user);
        // session
        putCookie(session, model);

        return "user-team";
    }

    @RequestMapping("/user/edit/{userId}")
    public String editUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId, Message message, ModelMap model) {

        User user = dao.get(User.class, userId);
        Hibernate.initialize(user.getMainActivity());

        if (!isAllowedForCurrentUser(session, user)) {
            return "redirect:/user/" + userId;
        }

        model.put("user", user);
        model.put("contactKeys", merge(Profile.CONTACT_KEYS, user.getProfile().getContacts().keySet()));
        model.put("socialKeys", merge(Profile.SOCIAL_KEYS, user.getProfile().getSocial().keySet()));
        model.put("message", message);
        // session
        putCookie(session, model);

        return "user-edit";
    }

    private String[] merge(String[] one, Collection<String> two) {
        Set<String> result = new TreeSet<String>();
        result.addAll(Arrays.asList(one));
        result.addAll(two);
        return result.toArray(new String[result.size()]);
    }

    @RequestMapping(value = "/user/edit/{userId}", method = RequestMethod.POST)
    public String saveUser(@CookieValue(required = false) Cookie session, @PathVariable("userId") long userId,
                           HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        // russian
        request.setCharacterEncoding("UTF8");
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

        model.put("message", new Message("���������� ���������� ���������"));

        return "ajax/message";
    }

    @RequestMapping(value = "/user/edit/password/{userId}", method = RequestMethod.POST)
    public String saveUserPassword(@CookieValue(required = false) Cookie session,
                                   @PathVariable("userId") long userId,
                                   @RequestParam("old") String old,
                                   @RequestParam("new") String newPassword,
                                   @RequestParam("again") String newPasswordAgain,
                                   ModelMap model) throws UnsupportedEncodingException {

        User user = dao.get(User.class, userId);

        if (!user.getPassword().equals(Utils.md5(old))) {
            model.put("message", new Message(Message.PASSWORD_OLD_WRONG, false));
        } else if (!newPassword.equals(newPasswordAgain)) {
            model.put("message", new Message(Message.PASSWORD_NOT_MATCH, false));
        } else if (newPassword.length() < 4) {
            model.put("message", new Message(Message.PASSWORD_TOO_SHORT, false));
        } else {
            user.setPassword(Utils.md5(newPassword));
            dao.save(user);

            model.put("message", new Message(Message.PASSWORD_UPDATED));
        }

        return "ajax/message";
    }


}
