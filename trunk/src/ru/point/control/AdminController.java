package ru.point.control;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.point.model.Project;
import ru.point.model.Session;

import javax.servlet.http.Cookie;

/**
 * @author Mikhail Sedov {02.08.2009}
 */
@Controller
@Transactional
public class AdminController extends AbstractController {

    private void checkAdminPermissions(Cookie sessionCookie) {
        if (sessionCookie == null) {
            throw new RuntimeException("");
        }

        Session session = dao.get(Session.class, sessionCookie.getValue());
        if (session != null) {
            if (!session.isAdmin()) {
                throw new RuntimeException("");
            }
        }
    }

    @RequestMapping("/admin/projects")
    public String adminProjects(@CookieValue(required = false) Cookie sessionCookie, ModelMap model) {

        try {
            checkAdminPermissions(sessionCookie);
        } catch (RuntimeException e) {
            //
            return "error";
        }

        model.put("projects", dao.filter(Project.class, "from Project where parent = null"));
        putCookie(sessionCookie, model);
        return "admin/projects";
    }

    @RequestMapping("/admin/people")
    public String adminPeople(@CookieValue(required = false) Cookie session, ModelMap model) {
        putCookie(session, model);
        return "admin/people";
    }

    @RequestMapping("/admin/misc")
    public String adminMisc(@CookieValue(required = false) Cookie session, ModelMap model) {
        putCookie(session, model);
        return "admin/misc";
    }

    @RequestMapping("/admin/managers")
    public String adminManagers(@CookieValue(required = false) Cookie session, ModelMap model) {
        putCookie(session, model);
        return "admin/managers";
    }

}
