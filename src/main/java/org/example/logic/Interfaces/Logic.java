package org.example.logic.Interfaces;

import java.util.List;

public interface Logic {
    List<ICapsule> readAllCapsules();
    void createCapsule(ICapsule capsule);
    ICapsule readCapsule(long id);
    void updateCapsule(ICapsule capsule);
    void deleteCapsule(long id);

    List<IVault> readAllInvestments();
    void createInvestment(IVault investment);
    IVault readInvestment(long id);
    void updateInvestment(IVault investment);
    void deleteInvestment(long id);
}
