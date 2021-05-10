package org.example.logic.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Investment {

    long getId();
    void setId(long id);

    String getName();
    void setName(String name);

    Map<Container,Long> getAllContainers();
    void setAllContainers(Map<Container,Long> map);
    Set<Container> getContainers();
    void addContainers(Container container, long amount);
    void removeContainers(Container container);

    Investment populate(long id, String name);

}
