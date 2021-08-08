package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ICapsule;
import org.example.logic.interfaces.sub.ICapsuleDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class CapsuleDomain implements ICapsuleDomain {

    private static CapsuleDomain instance;

    private CapsuleDomain(){

    }

    public static CapsuleDomain getInstance(){
        return instance == null ? instance = new CapsuleDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ICapsule> capsulesCache;

    @Override
    public List<ICapsule> readAllCapsules() {
        if (capsulesCache == null) {
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Capsules" + ConsoleColors.RESET);
            capsulesCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllCapsules()) {
                ICapsule newCapsule = CREATOR.emptyCapsule().convert2Obj(o);
                capsulesCache.add(newCapsule);
                System.out.println("Capsule [" + ConsoleColors.BLUE + newCapsule.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + capsulesCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return capsulesCache;
    }

    @Override
    public void createCapsule(ICapsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().createCapsule(capsule.convert2JSON());
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
        DATA_FACADE.getDataConnection().updateCapsule(capsule.convert2JSON());
        capsulesCache.removeIf(cap -> cap.getId() == capsule.getId());
        capsulesCache.add(capsule);
    }

    @Override
    public void deleteCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().deleteCapsule(id);
        capsulesCache.removeIf(capsule -> capsule.getId() == id);
    }
}
