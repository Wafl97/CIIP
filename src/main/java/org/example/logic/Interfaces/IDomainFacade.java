package org.example.logic.Interfaces;

import org.example.data.Interfaces.IDataFacade;

public interface IDomainFacade {

    void setSelectedInvestment(Investment investment);
    Investment getSelectedInvestment();
    void setSelectedItem(IItem item);
    IItem getSelectedItem();

    Logic getDomain();
    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
    Factory getFactory();
}
