package org.example.logic.interfaces;

import org.example.logic.dto.interfaces.comps.Identifiable;

import java.util.List;

public interface IGenericDomain {
    List<Identifiable> readAll();
    boolean create(Identifiable item);
    Identifiable read(long id);
    boolean update(Identifiable item);
    boolean delete(long id);
}
