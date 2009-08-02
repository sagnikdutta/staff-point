package ru.point.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ru.point.dao.GenericDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Mikhail Sedov {07.04.2009}
 */
public class BaseDao<Type, Key extends Serializable>
        extends HibernateDaoSupport
        implements GenericDao<Type, Key> {

    private Class<Type> clazz;

    public BaseDao(Class<Type> clazz) {
        this.clazz = clazz;
    }

    @Autowired
    public void autowiredSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @SuppressWarnings(value = "unchecked")
    public Type save(Type entity) {
        return (Type) getHibernateTemplate().save(entity);
    }

    public Type get(Key id) {
        return getHibernateTemplate().get(clazz, id);
    }

    public void delete(Type entity) {
        getHibernateTemplate().delete(entity);
    }

    public void update(Type entity) {
        getHibernateTemplate().update(entity);
    }

    public List<Type> listAll() {
        return getHibernateTemplate().loadAll(clazz);
    }

    public long countAll() {
        return ((Number) getSession(false).createQuery("select count(*) from " + clazz.getName()).list().get(0)).longValue();
    }

    public long countByProperty(String property, Object value) {
        return 0;
    }

    public long countByProperties(Map<String, Object> properties) {
        return 0;
    }

    public List<Type> findByProperty(String property, Object value) {
        return null;
    }

    public List<Type> findByProperties(Map<String, Object> properties) {
        return null;
    }

    public List<Type> findAllByPagination(int pageSize, int pageNumber) {
        return null;
    }

    public List<Type> findByPropertyPagination(String paramName, Object value, int pageSize, int pageNumber) {
        return null;
    }

    public List<Type> findByPropertiesPagination(Map<String, Object> paramMap, int pageSize, int pageNumber) {
        return null;
    }
}
