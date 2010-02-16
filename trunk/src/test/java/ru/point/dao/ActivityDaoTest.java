package ru.point.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.point.model.Activity;
import ru.point.model.User;

/**
 * Created by Mikhail Sedov [17.02.2010]
 */
public class ActivityDaoTest extends BaseDaoTest {

    @Test
    public void crud() {

        User user = new User();
        user.setLogin("s@s.ru");
        user.setPassword("qwerty");

        userDao.create(user);

        Activity activity = new Activity();
        activity.setUser(user);

        activityDao.create(activity);

        activity.setMain(true);

        activityDao.update(activity);

        activityDao.delete(activity.getId());
    }

}
