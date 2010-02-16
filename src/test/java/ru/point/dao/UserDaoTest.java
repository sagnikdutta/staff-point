package ru.point.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.point.model.User;

import java.util.Map;

/**
 * Created by Mikhail Sedov [23.01.2010]
 */
public class UserDaoTest extends BaseDaoTest {

    @Test
    public void crud() {

        User user = new User();
        user.setLogin("s@s.ru");
        user.setPassword("qwerty");

        userDao.create(user);

        User stored = userDao.retrieve(user.getId());

        Assert.assertEquals(stored.getLogin(), user.getLogin());

        stored = userDao.signupUser("s@s.ru", "qwerty");

        Assert.assertNotNull(stored);
        Assert.assertEquals(stored.getLogin(), user.getLogin());

        user.setLogin("s@s.com");
        user.setPassword("qwe123");
        userDao.update(user);

        stored = userDao.signupUser("s@s.com", "qwe123");

        Assert.assertNotNull(stored);
        Assert.assertEquals(stored.getLogin(), user.getLogin());

        userDao.delete(user.getId());

        Assert.assertNull(userDao.retrieve(user.getId()));

    }

    @Test
    public void find() {

        String[] logins = {
                "001", "002", "003", "004", "005",
                "006", "007", "008", "009", "010",
        };

        for (String login : logins) {
            User user = new User();
            user.setLogin(login);
            user.setPassword("qwerty");
            userDao.create(user);
        }

        Map<Long, User> users = userDao.retrieveAll();

        Assert.assertEquals(users.size(), logins.length);

    }

}
