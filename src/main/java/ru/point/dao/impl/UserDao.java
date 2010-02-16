package ru.point.dao.impl;

import org.springframework.stereotype.Repository;
import ru.point.model.User;

import java.util.List;

/**
 * @author Mikhail Sedov {07.04.2009}
 */
@Repository
public class UserDao extends BaseDao<User, Long> {

    public UserDao() {
        super(User.class);
    }

    @SuppressWarnings("unchecked")
    public List<User> findByName(String name) {
        return getSession(false).createQuery("from User where profile.firstName like ':query%' or profile.secondName " +
                "like ':query%' order by id")
                .setString("query", name)
                .list();
    }


}
