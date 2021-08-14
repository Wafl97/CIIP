package org.example.logic.sub;

import org.example.logic.ActionWriter;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.sub.IGenericDomain;

import java.util.List;

public abstract class GenericDomain<T> implements IGenericDomain<T> {

    private final IActionWriter actionWriter = new ActionWriter();

    @Override
    public List<T> readAll() {
        return null;
    }

    @Override
    public boolean create(T item) {
        return false;
    }

    @Override
    public T read(long id) {
        return null;
    }

    @Override
    public boolean update(T item) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
