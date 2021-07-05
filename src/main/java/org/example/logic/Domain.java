package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.DataConnection;
import org.example.logic.interfaces.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private final DataConnection CONNECTION = DataFacade.getInstance().getDataConnection();

    private final Factory CREATOR = StructureCreator.getInstance();

    private List<ICapsule> capsulesCache;
    private List<IVault> vaultCache;
    private List<ISkin> skinCache;

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){
        System.out.println("Starting");
        System.out.println("Connection type:\t" + CONNECTION.getClass().getSimpleName());
        System.out.println("Connection success:\t" + CONNECTION.connect());
        System.out.println("Start complete");
    }

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
    public List<ISkin> readAllSkins() {
        if (skinCache == null){
            skinCache = new ArrayList<>();
            for (Object o : CONNECTION.readAllSkins()) {
                ISkin newSkin = CREATOR.emptySkin().convert2Obj((JSONObject) o);
                skinCache.add(newSkin);
            }
        }
        return skinCache;
    }

    @Override
    public void createSkin(ISkin skin) {
        if (skinCache == null) readAllCapsules();
        CONNECTION.createSkin(skin.convert2JSON());
        skinCache.add(skin);
    }

    @Override
    public ISkin readSkin(long id) {
        if (skinCache == null) readAllSkins();
        return skinCache.stream().filter(skin -> skin.getId() == id).findFirst().get();
    }

    @Override
    public void updateSkin(ISkin skin) {
        if (skinCache == null) readAllSkins();
        CONNECTION.updateSkin(skin.convert2JSON());
        long id = skin.getId();
        skinCache.removeIf(skn -> skn.getId() == id);
        skinCache.add(skin);
    }

    @Override
    public void deleteSkin(long id) {
        if (skinCache == null) readAllSkins();
        CONNECTION.deleteSKin(id);
        skinCache.removeIf(skin -> skin.getId() == id);
    }

    @Override
    public List<IVault> readAllVaults() {
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
    public void createVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        CONNECTION.createVault(vault.convert2JSON());
        vaultCache.add(vault);
    }

    @Override
    public IVault readVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        return vaultCache.stream().filter(vault -> vault.getId() == id).findAny().get();
    }

    @Override
    public void updateVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        CONNECTION.updateVault(vault.convert2JSON());
        long id = vault.getId();
        vaultCache.removeIf(v -> v.getId() == id);
        vaultCache.add(vault);
    }

    @Override
    public void deleteVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        CONNECTION.deleteVault(id);
        vaultCache.removeIf(vault -> vault.getId() == id);
    }

}
