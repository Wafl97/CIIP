package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.ActionWriter;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.IGraffiti;
import org.example.logic.interfaces.sub.IGraffitiDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class GraffitiDomain implements IGraffitiDomain {

    private static GraffitiDomain instance;

    private GraffitiDomain(){

    }

    public static GraffitiDomain getInstance(){
        return instance == null ? instance = new GraffitiDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();
    private static final IActionWriter WRITER = new ActionWriter();
    private static final String TYPE = "Graffiti";

    private List<IGraffiti> graffitiCache;

    @Override
    public List<IGraffiti> readAllGraffities() {
        if (graffitiCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Graffities" + ConsoleColors.RESET);
            graffitiCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllGraffities()){
                IGraffiti newGraffiti = CREATOR.emptyGraffiti().convert2Obj(o);
                graffitiCache.add(newGraffiti);
                System.out.println("Graffiti [" + ConsoleColors.BLUE + newGraffiti.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + graffitiCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return graffitiCache;
    }

    @Override
    public void createGraffiti(IGraffiti graffiti) {
        if (graffitiCache == null) readAllGraffities();
        DATA_FACADE.getDataConnection().createGraffiti(graffiti.convert2JSON());
        graffitiCache.add(graffiti);
        WRITER.printAction(ConsoleColors.GREEN,"Created",TYPE,graffiti.getId(),true);
    }

    @Override
    public IGraffiti readGraffiti(long id) {
        if (graffitiCache == null) readAllGraffities();
        WRITER.printAction(ConsoleColors.YELLOW,"Read",TYPE,id,false);
        return graffitiCache.stream().filter(graffiti -> graffiti.getId() == id).findFirst().get();
    }

    @Override
    public void updateKGraffiti(IGraffiti graffiti) {
        if (graffitiCache == null) readAllGraffities();
        DATA_FACADE.getDataConnection().updateGraffiti(graffiti.convert2JSON());
        graffitiCache.removeIf(grf -> grf.getId() == graffiti.getId());
        graffitiCache.add(graffiti);
        WRITER.printAction(ConsoleColors.PURPLE,"Updated",TYPE,graffiti.getId(),true);
    }

    @Override
    public void deleteGraffiti(long id) {
        if (graffitiCache == null) readAllGraffities();
        DATA_FACADE.getDataConnection().deleteGraffiti(id);
        graffitiCache.removeIf(graffiti -> graffiti.getId() == id);
        WRITER.printAction(ConsoleColors.RED,"Deleted",TYPE,id,true);
    }
}
