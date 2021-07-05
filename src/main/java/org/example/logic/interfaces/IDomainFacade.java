package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;

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
