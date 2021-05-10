package org.example.logic;

import org.example.data.DataConnection;
import org.example.data.GFX;
import org.example.data.JsonConnection;
import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Investment;
import org.example.logic.Interfaces.Logic;

import java.util.List;

public class Domain implements Logic {

    private static Domain instance;

    private static final DataConnection CONNECTION = JsonConnection.getInstance();

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){}

    @Override
    public List<Container> readAllContainers(){
        return CONNECTION.readAllContainers();
    }

    @Override
    public void createContainer(Container container) {
        CONNECTION.createContainer(container);
    }

    @Override
    public Container readContainer(long id) {
        return CONNECTION.readContainer(id);
    }

    @Override
    public void updateContainer(long id, Container container) {
        CONNECTION.updateContainer(id,container);
    }

    @Override
    public void deleteContainer(long id) {
        CONNECTION.deleteContainer(id);
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

    @Override
    public GFX getGFX() {
        return GFX.getInstance();
    }
}
