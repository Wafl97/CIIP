package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.Interfaces.DataConnection;
import org.example.logic.Interfaces.Factory;
import org.example.logic.Interfaces.ICapsule;
import org.example.logic.Interfaces.IVault;
import org.example.logic.Interfaces.Logic;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private final DataConnection CONNECTION = DataFacade.getInstance().getDataConnection();

    private final Factory CREATOR = StructureCreator.getInstance();

    private List<ICapsule> capsulesCache;
    private List<IVault> vaultCache;

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){}

    @Override
    public List<ICapsule> readAllCapsules() {
        if (capsulesCache == null) {
            capsulesCache = new ArrayList<>();
            for (Object o : CONNECTION.readAllCapsules()) {
                ICapsule newCapsule = CREATOR.emptyCapsule().convert2Obj((JSONObject) o);
                capsulesCache.add(newCapsule);
            }
        }
        return capsulesCache;
    }

    @Override
    public void createCapsule(ICapsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        CONNECTION.createCapsule(capsule.convert2JSON());
        capsulesCache.add(capsule);
    }

    @Override
    public ICapsule readCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        return capsulesCache.stream().filter(capsule -> capsule.getId() == id).findFirst().get();
    }

    @Override
    public void updateCapsule(ICapsule capsule) {
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
    public List<IVault> readAllInvestments() {
        if (vaultCache == null) {
            vaultCache = new ArrayList<>();
            for (Object o : CONNECTION.readAllVaults()) {
                IVault newVault = CREATOR.emptyVault().convert2Obj((JSONObject) o);
                vaultCache.add(newVault);
            }
        }
        return vaultCache;
    }

    @Override
    public void createInvestment(IVault vault) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.createVault(vault.convert2JSON());
        vaultCache.add(vault);
    }

    @Override
    public IVault readInvestment(long id) {
        if (vaultCache == null) readAllInvestments();
        return vaultCache.stream().filter(vault -> vault.getId() == id).findAny().get();
    }

    @Override
    public void updateInvestment(IVault investment) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.updateVault(investment.convert2JSON());
        long id = investment.getId();
        vaultCache.removeIf(vault -> vault.getId() == id);
        vaultCache.add(investment);
    }

    @Override
    public void deleteInvestment(long id) {
        if (vaultCache == null) readAllInvestments();
        CONNECTION.deleteVault(id);
        vaultCache.removeIf(vault -> vault.getId() == id);
    }

}
