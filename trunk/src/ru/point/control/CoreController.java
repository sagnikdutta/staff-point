package ru.point.control;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.point.model.Project;

import javax.servlet.http.Cookie;

/**
 * @author: Mikhail Sedov [25.03.2009]
 */
@Controller
@Transactional
public class CoreController extends AbstractController {

    @RequestMapping("/")
    public ModelAndView index(@CookieValue(required = false) Cookie session,
                              ModelMap model) {
        model.put("projects", dao.filter(Project.class, "from Project where parent = null"));
        putCookie(session, model);
        return new ModelAndView("index", model);
    }
}
