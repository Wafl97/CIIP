package org.example.data;

import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Investment;

import java.util.List;

public interface DataConnection {

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(long id, Investment investment);
    void deleteInvestment(long id);

    List<Container> readAllContainers();
    void createContainer(Container container);
    Container readContainer(long id);
    void updateContainer(long id, Container container);
    void deleteContainer(long id);

}
