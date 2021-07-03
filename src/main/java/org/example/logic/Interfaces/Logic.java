package org.example.logic.Interfaces;

import org.example.logic.Capsule;

import java.util.List;

public interface Logic {
    List<Capsule> readAllCapsules();
    void createCapsule(Capsule capsule);
    Capsule readCapsule(long id);
    void updateCapsule(Capsule capsule);
    void deleteCapsule(long id);

    List<Investment> readAllInvestments();
    void createInvestment(Investment investment);
    Investment readInvestment(long id);
    void updateInvestment(Investment investment);
    void deleteInvestment(long id);
}
