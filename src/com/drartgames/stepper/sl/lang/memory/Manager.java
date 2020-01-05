package com.drartgames.stepper.sl.lang.memory;

import java.util.List;

public interface Manager<T extends Entity> {
    boolean has(String name);

    boolean add(T entity);

    boolean remove(String name);

    List<T> getAll();

    T getByName(String name);
}
