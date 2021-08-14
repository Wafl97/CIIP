package org.example.logic.interfaces;

import org.example.logic.dto.interfaces.comps.Identifiable;

import java.util.List;

public interface IGenericDomain {
    // TODO: 13-08-2021 this is going to replace the other domains
    List<Identifiable> readAll();
    boolean create(Identifiable item);
    Identifiable read(long id);
    boolean update(Identifiable item);
    boolean delete(long id);
}
