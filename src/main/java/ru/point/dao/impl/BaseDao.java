package ru.point.dao.impl;

/**
 * Created by Mikhail Sedov [23.01.2010]
 */
public interface BaseDao<PK, E> {

    public boolean create(E entity);
    public E update(E entity);
    public E retrieve(PK entity);
    public boolean delete(PK entity);

}
