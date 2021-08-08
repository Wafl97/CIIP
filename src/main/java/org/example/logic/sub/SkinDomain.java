package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ISkin;
import org.example.logic.interfaces.sub.ISkinDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class SkinDomain implements ISkinDomain {

    private static SkinDomain instance;

    private SkinDomain(){

    }

    public static SkinDomain getInstance(){
        return instance == null ? instance = new SkinDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ISkin> skinCache;

    @Override
    public List<ISkin> readAllSkins() {
        if (skinCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Skins" + ConsoleColors.RESET);
            skinCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllSkins()) {
                ISkin newSkin = CREATOR.emptySkin().convert2Obj(o);
                skinCache.add(newSkin);
                System.out.println("Skin [" + ConsoleColors.BLUE + newSkin.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + skinCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return skinCache;
    }

    @Override
    public void createSkin(ISkin skin) {
        if (skinCache == null) readAllSkins();
        DATA_FACADE.getDataConnection().createSkin(skin.convert2JSON());
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
        DATA_FACADE.getDataConnection().updateSkin(skin.convert2JSON());
        skinCache.removeIf(skn -> skn.getId() == skin.getId());
        skinCache.add(skin);
    }

    @Override
    public void deleteSkin(long id) {
        if (skinCache == null) readAllSkins();
        DATA_FACADE.getDataConnection().deleteSKin(id);
        skinCache.removeIf(skin -> skin.getId() == id);
    }
}
