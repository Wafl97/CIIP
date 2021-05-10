package org.example.logic.Interfaces;

import org.example.data.GFX;
import org.example.logic.Interfaces.Container;

import java.util.List;
import java.util.Set;

public interface Logic {
    List<Container> readAllContainers();
    void createContainer(Container container);
    Container readContainer(long id);
    void updateContainer(long id, Container container);
    void deleteContainer(long id);

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(long id,Investment investment);
    void deleteInvestment(long id);

    GFX getGFX();
}
