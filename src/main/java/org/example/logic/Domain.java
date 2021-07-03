package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.Interfaces.DataConnection;
import org.example.logic.Interfaces.Factory;
import org.example.logic.Interfaces.Investment;
import org.example.logic.Interfaces.Logic;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private final DataConnection CONNECTION = DataFacade.getInstance().getDataConnection();

    private final Factory CREATOR = StructureCreator.getInstance();

    private List<Capsule> capsulesCache;
    private List<Investment> vaultCache;

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){}

    @Override
    public List<Capsule> readAllCapsules() {
        if (capsulesCache == null) {
            System.out.println("NOT CACHED");
            capsulesCache = new ArrayList<>();
            for (Object o : CONNECTION.readAllCapsules()) {
                Capsule newCapsule = CREATOR.emptyCapsule().convert2Obj((JSONObject) o);
                capsulesCache.add(newCapsule);
            }
        }
        System.out.println("IS CACHED");
        return capsulesCache;
    }

    @Override
    public void createCapsule(Capsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        CONNECTION.createCapsule(capsule.convert2JSON());
        capsulesCache.add(capsule);
    }

    @Override
    public Capsule readCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        return capsulesCache.stream().filter(capsule -> capsule.getId() == id).findAny().get();
    }

    @Override
    public void updateCapsule(Capsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        CONNECTION.updateCapsule(capsule.convert2JSON());
        long id = capsule.getId();
        capsulesCache.removeIf(cap -> cap.getId() == id);
        capsulesCache.add(capsule);
    }

    @Override
    public void deleteCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        CONNECTION.deleteCapsule(id);
        capsulesCache.removeIf(capsule -> capsule.getId() == id);
    }

    @Override
    public List<Investment> readAllInvestments() {
        if (vaultCache == null) {
            System.out.println("GETTING CACHED");
            vaultCache = new ArrayList<>();
            for (Object o : CONNECTION.readAllInvestments()) {
                Vault newInvestment = CREATOR.emptyVault().convert2Obj((JSONObject) o);
                vaultCache.add(newInvestment);
            }
        }
        return vaultCache;
    }

    @Override
    public void createInvestment(Investment investment) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.createInvestment(investment.convert2JSON());
        vaultCache.add(investment);
    }

    @Override
    public Investment readInvestment(long id) {
        if (vaultCache == null) readAllInvestments();
        return vaultCache.stream().filter(vault -> vault.getId() == id).findAny().get();
    }

    @Override
    public void updateInvestment(Investment investment) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.updateInvestment(investment.convert2JSON());
        long id = investment.getId();
        vaultCache.removeIf(vault -> vault.getId() == id);
        vaultCache.add(investment);
    }

    @Override
    public void deleteInvestment(long id) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.deleteInvestment(id);
        vaultCache.removeIf(vault -> vault.getId() == id);
    }

}
