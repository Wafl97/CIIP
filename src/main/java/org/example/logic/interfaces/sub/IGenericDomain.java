package org.example.logic.interfaces.sub;

import java.util.List;

public interface IGenericDomain<T> {

    List<T> readAll();
    boolean create(T item);
    T read(long id);
    boolean update(T item);
    boolean delete(long id);
}
