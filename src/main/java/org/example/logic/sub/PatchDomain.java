package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.IPatch;
import org.example.logic.interfaces.sub.IPatchDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class PatchDomain implements IPatchDomain {

    private static PatchDomain instance;

    private PatchDomain(){

    }

    public static PatchDomain getInstance(){
        return instance == null ? instance = new PatchDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<IPatch> patchCache;

    @Override
    public List<IPatch> readAllPatches() {
        if (patchCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Patches" + ConsoleColors.RESET);
            patchCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllPatches()) {
                IPatch newPatch = CREATOR.emptyPatch().convert2Obj(o);
                patchCache.add(newPatch);
                System.out.println("Patch [" + ConsoleColors.BLUE + newPatch.getName() + ConsoleColors.RESET + "] Cached");
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + patchCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return patchCache;
    }

    @Override
    public void createPatch(IPatch patch) {
        if (patchCache == null) readAllPatches();
        DATA_FACADE.getDataConnection().createPatch(patch.convert2JSON());
        patchCache.add(patch);
    }

    @Override
    public IPatch readPatch(long id) {
        if (patchCache == null) readAllPatches();
        return patchCache.stream().filter(patch -> patch.getId() == id).findFirst().get();
    }

    @Override
    public void updatePatch(IPatch patch) {
        if (patchCache == null) readAllPatches();
        DATA_FACADE.getDataConnection().updatePatch(patch.convert2JSON());
        patchCache.removeIf(ptc -> ptc.getId() == patch.getId());
        patchCache.add(patch);
    }

    @Override
    public void deletePatch(long id) {
        if (patchCache == null) readAllPatches();
        DATA_FACADE.getDataConnection().deletePatch(id);
        patchCache.removeIf(patch -> patch.getId() == id);
    }
}
