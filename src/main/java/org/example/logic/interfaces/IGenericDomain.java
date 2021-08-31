package org.example.logic.interfaces;

import org.example.logic.dto.interfaces.comps.Transferable;

import java.util.List;

public interface IGenericDomain<T> {

    String getType();

    int cacheSize();

    List<Transferable<T>> readAll();
    boolean create(Transferable<T> item);
    Transferable<T> read(long id);
    boolean update(Transferable<T> item);
    boolean delete(long id);
}
