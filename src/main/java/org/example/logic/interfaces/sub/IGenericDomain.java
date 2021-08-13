package org.example.logic.interfaces.sub;

import java.util.List;

public interface IGenericDomain<T> {
    // TODO: 13-08-2021 this is going to replace the other domains
    List<T> readAll();
    boolean create(T item);
    T read(long id);
    boolean update(T item);
    boolean delete(long id);
}
