package org.example.logic;

import org.example.data.*;
import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Investment;
import org.example.logic.Interfaces.Logic;

import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private static final DataConnection CONNECTION = JsonConnection.getInstance();

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){}

    @Override
    public List<IItem> readAllContainers(){
        return CONNECTION.readAllCapsules();
    }

    @Override
    public void createContainer(IItem container) {
        CONNECTION.createCapsule(container);
    }

    @Override
    public IItem readContainer(long id) {
        return CONNECTION.readCapsule(id);
    }

    @Override
    public void updateContainer(long id, IItem container) {
        CONNECTION.updateCapsule(id,container);
    }

    @Override
    public void deleteContainer(long id) {
        CONNECTION.deleteCapsule(id);
    }

    @Override
    public List<Investment> readAllInvestments() {
        return CONNECTION.readAllInvestments();
    }

    @Override
    public void createInvestment(Investment investment) {
        CONNECTION.createInvestment(investment);
    }

    @Override
    public Investment readInvestment(long id) {
        return CONNECTION.readInvestment(id);
    }

    @Override
    public void updateInvestment(long id, Investment investment) {
        CONNECTION.updateInvestment(id,investment);
    }

    @Override
    public void deleteInvestment(long id) {
        CONNECTION.deleteInvestment(id);
    }

}
