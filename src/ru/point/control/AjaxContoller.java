package ru.point.control;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.point.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: Mikhail Sedov [28.04.2009]
 */
@Controller
@Transactional
public class AjaxContoller extends AbstractController {

    @RequestMapping("/ajax/users")
    public String getUsers(HttpServletRequest request,
                           ModelMap model) throws UnsupportedEncodingException {

        request.setCharacterEncoding("CP1251");
        String query = request.getParameter("q");

        List<User> users = dao.filter(User.class, "from User where profile.firstName like '" + query + "%' or " +
                "profile.secondName like '" + query + "%' order by id ", 10);

        model.put("users", users);

        return "ajax/user";

    }

    @RequestMapping("/ajax/project/{projectId}/users")
    public String getUsersFromProject(HttpServletRequest request,
                                      @PathVariable("projectId") int projectId,
                                      ModelMap model) throws UnsupportedEncodingException {

        request.setCharacterEncoding("CP1251");
        String query = request.getParameter("q");

        List<User> users = dao.filter(User.class, "from User where profile.firstName like '" + query + "%' or " +
                "profile.secondName like '" + query + "%' order by id ", 10);

        model.put("users", users);

        return "ajax/user";

    }

}
