package ru.point.dao;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.point.BaseTest;
import ru.point.dao.impl.DatabaseManager;

/**
 * Created by Mikhail Sedov [17.02.2010]
 */
public class BaseDaoTest extends BaseTest {

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected ActivityDao activityDao;

    @Autowired
    private DatabaseManager databaseManager;

    @Before
    public void clean() {
        userDao.deleteAll();
        activityDao.deleteAll();
    }

}
