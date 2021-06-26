package org.example.logic.Interfaces;

import java.util.List;

public interface Logic {
    List<IItem> readAllContainers();
    void createContainer(IItem container);
    IItem readContainer(long id);
    void updateContainer(long id, IItem container);
    void deleteContainer(long id);

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(long id,Investment investment);
    void deleteInvestment(long id);
}
