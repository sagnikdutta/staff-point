package ru.point.dao;

import org.springframework.stereotype.Repository;
import ru.point.dao.impl.BaseDao;
import ru.point.dao.impl.BaseDaoImpl;
import ru.point.model.Activity;

/**
 * Created by Mikhail Sedov [16.02.2010]
 */
@Repository
public class ActivityDao extends BaseDaoImpl<Long, Activity> implements BaseDao<Long, Activity> {

    public ActivityDao() {
        super(Long.class, Activity.class);
    }



}
