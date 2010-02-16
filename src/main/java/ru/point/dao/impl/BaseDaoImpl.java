package ru.point.dao.impl;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Mikhail Sedov [23.01.2010]
 */
@Repository
public abstract class BaseDaoImpl<PK, E> implements BaseDao<PK, E> {

    @Autowired
    protected DatabaseManager dbManager;

    protected transient PrimaryIndex<PK, E> entitiesById;

    private Class<E> entityClazz;
    private Class<PK> keyClazz;

    protected BaseDaoImpl(Class<PK> keyClazz, Class<E> entityClazz) {
        this.keyClazz = keyClazz;
        this.entityClazz = entityClazz;
    }

    protected EntityStore getStore() {
        return dbManager.getEntityStore();
    }

    public void init() throws DatabaseException {
        entitiesById = getStore().getPrimaryIndex(keyClazz, entityClazz);
    }

    @Override
    public boolean create(E entity) {
        return entitiesById.putNoOverwrite(entity);
    }

    @Override
    public E update(E entity) {
        return entitiesById.put(entity);
    }

    @Override
    public E retrieve(PK entity) {
        return entitiesById.get(entity);
    }

    @Override
    public boolean delete(PK entity) {
        return entitiesById.delete(entity);
    }
}
