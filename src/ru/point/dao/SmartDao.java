package ru.point.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import ru.point.model.Report;

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
        Map <String, Long> result = new HashMap<String, Long>();
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
        Property prop = Property.forName("reportForActivity");
        return getSession(false).createCriteria(Report.class)
                .createAlias("reportForActivity","activity")
                .add(Restrictions.eq("activity.user.id", userId))
                .list();
    }
}
