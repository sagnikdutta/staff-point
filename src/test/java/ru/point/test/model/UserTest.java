package ru.point.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.point.dao.SmartDao;
import ru.point.model.User;
import ru.point.model.Activity;
import ru.point.control.PeopleController;

import java.util.List;

/**
 * @author: Mikhail Sedov [13.01.2009]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:./cnf/web/beans.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional(noRollbackFor = Throwable.class)
public class UserTest {

    @Autowired
    private SmartDao dao;

    @Autowired
    private PeopleController userManager;

    @Test
    public void gettingUsers() {
        List<User> persons = dao.findAll(User.class);
        for (User user : persons) {
            try {
//                userManager.loginUser(user.getLogin(), user.getLogin() + "123", new ModelMap());

//                Assert.assertTrue(s.getUser().getId() == user.getId());
//                Set<Position> lp = user.getActivities();
//                for (Position position : lp) {
//                    Assert.assertTrue(position.getUser().getId() == user.getId());
//                }

                //userManager.logout();
            } catch (Exception e) {
                System.out.println(user + " could not login");
            }
        }
    }

    @Test
    public void settingReport() {
        List<User> persons = dao.findAll(User.class);
        for (User person : persons) {
            for (Activity position : person.getActivities()) {
                // userManager.saveReport(position, 2009, 5, "user " + person.getFullName() + " did nothing at " + position.getName());
            }
        }
    }

    @Test
    public void gettingReports() {
        List<User> persons = dao.findAll(User.class);
        for (User person : persons) {
            for (Activity position : person.getActivities()) {
//                List<Report> lr = userManager.generateReport(position, 2009, 5);
//                for (Report report : lr) {
//                    System.out.println(report);
//                }
            }
        }
    }
}
