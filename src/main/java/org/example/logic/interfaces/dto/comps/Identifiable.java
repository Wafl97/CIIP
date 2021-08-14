package org.example.logic.interfaces.dto.comps;

public interface Identifiable {

    long getId();
    void setId(long id);

    long findMaxID();

    String getName();
    void setName(String name);
}
