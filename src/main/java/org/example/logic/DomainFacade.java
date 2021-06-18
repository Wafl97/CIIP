package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.IDataFacade;
import org.example.logic.Interfaces.IDomainFacade;
import org.example.logic.Interfaces.IFileHandler;
import org.example.logic.Interfaces.Logic;

public class DomainFacade implements IDomainFacade {

    private static DomainFacade instance;

    private DomainFacade(){}

    public static DomainFacade getInstance(){
        return instance == null ? instance = new DomainFacade() : instance;
    }

    @Override
    public Logic getDomain() {
        return Domain.getInstance();
    }

    @Override
    public IDataFacade getDataFacade() {
        return DataFacade.getInstance();
    }

    @Override
    public IFileHandler getFileHandler() {
        return FileHandler.getInstance();
    }
}
