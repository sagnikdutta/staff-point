package ru.point.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import ru.point.model.Activity;
import ru.point.model.Report;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Component
public class SmartDao extends HibernateDaoSupport {

    @Autowired
    public void autowiredSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public <T> void save(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public <T> void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public <T> void delete(Class<T> type, Serializable id) {
        getHibernateTemplate().delete(get(type, id));
    }

    public <T> T get(Class<T> type, Serializable id) {
        return getHibernateTemplate().get(type, id);
    }

    public <T> List<T> filter(Class<T> type, String query) {
        return filter(type, query, null);
    }

    public Map<String, Long> stat(String queryString, Map<String, Object> param) {
        List queryResult = createQuery(queryString, param).list();
        //
        Map<String, Long> result = new HashMap<String, Long>();
        for (Object o : queryResult) {
            Object[] row = (Object[]) o;
            result.put((String) row[0], ((Number) row[1]).longValue());
        }
        return result;
    }

    public <T> List<T> filter(Class<T> type, String queryString, Map<String, Object> param) {
        return (List<T>) createQuery(queryString, param).list();
    }

    public List filter(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).list();
    }

    public <T> T findUniqueObject(Class<T> type, String query) {
        return findUniqueObject(type, query, null);
    }

    public <T> T findUniqueObject(Class<T> type, String queryString, Map<String, Object> param) {
        return (T) createQuery(queryString, param).uniqueResult();
    }

    public <T> List<T> findAll(Class<T> type) {
        return getHibernateTemplate().loadAll(type);
    }

    public void execute(String query) {
        getSession().createQuery(query).executeUpdate();
    }

    private Query createQuery(String queryString, Map<String, Object> param) {
        Session session = getSession(false);
        Query query = session.createQuery(queryString);
        if (param != null && !param.isEmpty()) {
            query.setProperties(param);
        }
        return query;
    }

    public List<Report> listReportsForUser(long userId) {
        return getSession(false)
                .createQuery("from Report as report where report.reportForActivity.user = :user order by report.id desc ")
                .setLong("user", userId)
                .list();
    }

    public Collection<Activity> getProjectActivitiesByBirthday(long projectId) {
        return getSession(false)
                .createQuery("from Activity as activity where activity.project = :project order by activity.user.profile.birthDay")
                .setLong("project", projectId)
                .list();
    }
}
