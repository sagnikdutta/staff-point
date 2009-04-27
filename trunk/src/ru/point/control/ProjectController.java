package ru.point.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;

import javax.servlet.http.Cookie;

import ru.point.model.Project;
import ru.point.model.Activity;
import ru.point.view.Group;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 * @author: Mikhail Sedov [27.04.2009]
 */
@Controller
@Transactional
public class ProjectController extends AbstractController {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Project

    @RequestMapping("/project")
    public String listProjects(@CookieValue(required = false) Cookie session, ModelMap model) {
        putCookie(session, model);

        return "projects";
    }

    @RequestMapping("/project/{projectId}")
    public String getProject(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();
        groups.add(new Group<Activity>("", project.getActivities()));

        model.put("groups", groups);
        putCookie(session, model);

        return "project";
    }

    @RequestMapping("/project/edit/{projectId}")
    public String editProject(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();
        groups.add(new Group<Activity>("", project.getActivities()));

        model.put("groups", groups);
        putCookie(session, model);

        return "project-edit";
    }

    @RequestMapping("/project/{projectId}/by/role")
    public String getProjectByRole(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
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

        return "project";
    }

    @RequestMapping("/project/{projectId}/by/name")
    public String getProjectByName(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();

        for (String first : new String[]{"À", "Á", "Â", "Ã", "Ä", "Å", "¨", "Æ", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î", "Ï", "Ð", "Ñ", "Ò", "Ó", "Ô", "Õ", "Ö", "×", "Ø", "Ù", "Ü", "Û", "Ú", "Ý", "Þ", "ß",}) {
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

        return "project";
    }

    @RequestMapping("/project/{projectId}/by/birthday")
    public String getProjectByBirthday(@CookieValue(required = false) Cookie session, @PathVariable("projectId") long projectId, ModelMap model) {
        Project project = dao.get(Project.class, projectId);
        Hibernate.initialize(project.getActivities());
        model.put("project", project);

        List<Group<Activity>> groups = new LinkedList<Group<Activity>>();
        groups.add(new Group<Activity>("", dao.getProjectActivitiesByBirthday(projectId)));

        model.put("groups", groups);
        model.put("groupby", "birthday");
        putCookie(session, model);

        return "project";
    }

}
