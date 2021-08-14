package org.example.logic.dto.interfaces.comps;

public interface Identifiable {

    long getId();
    void setId(long id);

    long findMaxID();

    String getName();
    void setName(String name);
}
