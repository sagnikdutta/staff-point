package ru.point.dao.impl;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

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

    @PostConstruct
    public void init() {
        initPrimaryIndex();
        initSecondaryIndex();
    }

    public void initPrimaryIndex() throws DatabaseException {
        entitiesById = getStore().getPrimaryIndex(keyClazz, entityClazz);
    }

    protected void initSecondaryIndex() {
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

    public Map<PK, E> retrieveAll() {
        return entitiesById.map();
    }

    @Override
    public boolean delete(PK entity) {
        return entitiesById.delete(entity);
    }

    public void deleteAll() {
        EntityCursor<E> ec = entitiesById.entities();
        try {
            for (E entity : ec) {
                ec.delete();
            }
        } finally {
            ec.close();
        }
    }


}
