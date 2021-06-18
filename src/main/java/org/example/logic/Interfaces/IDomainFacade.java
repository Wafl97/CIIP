package org.example.logic.Interfaces;

import org.example.data.IDataFacade;
import org.example.logic.FileHandler;

public interface IDomainFacade {

    Logic getDomain();
    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
}
