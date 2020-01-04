package com.drartgames.stepper.sl.lang.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultManager<T extends Entity> implements Manager<T> {
    private List<T> entities;

    public DefaultManager() {
        entities = new ArrayList<>();
    }

    @Override
    public boolean has(String name) {
        for (T entity : entities) {
            if (entity.getName().equals(name))
                return true;
        }

        return false;
    }

    @Override
    public boolean add(T entity) {
        for (T e : entities) {
            if (e.getName().equals(entity.getName()))
                return false;
        }

        entities.add(entity);;

        return true;
    }

    @Override
    public boolean remove(String name) {
        Iterator<T> iterator = entities.iterator();

        while (iterator.hasNext()) {
            T entity = iterator.next();

            if (entity.getName().equals(name)) {
                iterator.remove();

                return true;
            }
        }

        return false;
    }

    //@fixme this is a bucket of shit
    @Override
    public List<T> getAll() {
        return entities;
    }

    @Override
    public T getByName(String name) {
        for (T t : entities) {
            if (t.getName().equals(name))
                return t;
        }

        return null;
    }
}
