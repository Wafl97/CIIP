package org.example.logic.Interfaces;

import org.example.data.Interfaces.IDataFacade;

public interface IDomainFacade {

    void setSelectedInvestment(IVault investment);
    IVault getSelectedInvestment();
    void setSelectedCapsule(ICapsule capsule);
    ICapsule getSelectedCapsule();

    Logic getDomain();
    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
    Factory getFactory();
}
