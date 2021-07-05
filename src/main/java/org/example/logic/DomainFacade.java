package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.*;

public final class DomainFacade implements IDomainFacade {

    private IVault selectedInvestment;
    private ICapsule selectedCapsule;

    private static DomainFacade instance;

    private DomainFacade(){}

    public static DomainFacade getInstance(){
        return instance == null ? instance = new DomainFacade() : instance;
    }


    @Override
    public void setSelectedInvestment(IVault investment) {
        selectedInvestment = investment;
    }

    @Override
    public IVault getSelectedInvestment() {
        return selectedInvestment;
    }

    @Override
    public void setSelectedCapsule(ICapsule item) {
        selectedCapsule = item;
    }

    @Override
    public ICapsule getSelectedCapsule() {
        return selectedCapsule;
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

    @Override
    public Factory getFactory(){
        return StructureCreator.getInstance();
    }
}
