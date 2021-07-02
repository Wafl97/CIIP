package org.example.logic.Interfaces;

import java.util.List;

public interface Logic {
    List<IItem> readAllContainers();
    void createContainer(IItem container);
    IItem readContainer(long id);
    void updateContainer(IItem container);
    void deleteContainer(long id);

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(Investment investment);
    void deleteInvestment(long id);
}
