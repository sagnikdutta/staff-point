package ru.point.dao;

import com.sleepycat.persist.SecondaryIndex;
import org.springframework.stereotype.Repository;
import ru.point.dao.impl.BaseDaoImpl;
import ru.point.model.User;
import ru.point.utils.Utils;

import javax.annotation.PostConstruct;

/**
 * Created by Mikhail Sedov [16.02.2010]
 */
@Repository
public class UserDao extends BaseDaoImpl<Long, User> {

    protected SecondaryIndex<String, Long, User> userByLogin;

    protected UserDao() {
        super(Long.class, User.class);
    }

    @PostConstruct
    public void init() {
        super.init();
        userByLogin = getStore().getSecondaryIndex(entitiesById, String.class, "login");
    }

    public User signupUser(String login, String password) {

        User user = userByLogin.get(login);

        if (user == null || user.getPassword().equals(Utils.md5(password))) {
            return user;
        } else {
            throw new IllegalArgumentException("user/password is not valid");
        }
    }
}

