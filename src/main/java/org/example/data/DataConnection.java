package org.example.data;

import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Investment;

import java.util.List;

public interface DataConnection {

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(long id, Investment investment);
    void deleteInvestment(long id);

    List<IItem> readAllCapsules();
    void createCapsule(IItem capsule);
    IItem readCapsule(long id);
    void updateCapsule(long id, IItem capsule);
    void deleteCapsule(long id);

}
