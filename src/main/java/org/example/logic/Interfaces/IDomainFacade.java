package org.example.logic.Interfaces;

import org.example.data.Interfaces.IDataFacade;

public interface IDomainFacade {

    Logic getDomain();
    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
}
