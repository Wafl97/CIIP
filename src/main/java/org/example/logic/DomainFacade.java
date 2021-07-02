package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.Interfaces.IDataFacade;
import org.example.logic.Interfaces.*;

public class DomainFacade implements IDomainFacade {

    private Investment selectedInvestment;
    private IItem selectedItem;

    private static DomainFacade instance;

    private DomainFacade(){}

    public static DomainFacade getInstance(){
        return instance == null ? instance = new DomainFacade() : instance;
    }


    @Override
    public void setSelectedInvestment(Investment investment) {
        selectedInvestment = investment;
    }

    @Override
    public Investment getSelectedInvestment() {
        return selectedInvestment;
    }

    @Override
    public void setSelectedItem(IItem item) {
        selectedItem = item;
    }

    @Override
    public IItem getSelectedItem() {
        return selectedItem;
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
