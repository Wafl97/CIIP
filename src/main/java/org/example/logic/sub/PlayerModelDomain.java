package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.ActionWriter;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.IPlayerModel;
import org.example.logic.interfaces.sub.IPlayerModelDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class PlayerModelDomain implements IPlayerModelDomain {

    private static PlayerModelDomain instance;

    private PlayerModelDomain(){

    }

    public static PlayerModelDomain getInstance(){
        return instance == null ? instance = new PlayerModelDomain(): instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();
    private static final IActionWriter WRITER = new ActionWriter();
    private static final String TYPE = "PlayerModel";

    private List<IPlayerModel> playerModelCache;

    @Override
    public List<IPlayerModel> readAllPlayerModels() {
        if (playerModelCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching PlayerModels" + ConsoleColors.RESET);
            playerModelCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllPlayerModels()) {
                IPlayerModel newPlayerModel = CREATOR.emptyPlayerModel().convert2Obj(o);
                playerModelCache.add(newPlayerModel);
                System.out.println("PlayerModel [" + ConsoleColors.BLUE + newPlayerModel.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + "Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + playerModelCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return playerModelCache;
    }

    @Override
    public void createPlayerModel(IPlayerModel playerModel) {
        if (playerModelCache == null) readAllPlayerModels();
        DATA_FACADE.getDataConnection().createPlayerModel(playerModel.convert2JSON());
        playerModelCache.add(playerModel);
        WRITER.printAction(ConsoleColors.GREEN,"Created",TYPE,playerModel.getId());
    }

    @Override
    public IPlayerModel readPlayerModel(long id) {
        if (playerModelCache == null) readAllPlayerModels();
        WRITER.printAction(ConsoleColors.YELLOW,"Read",TYPE,id);
        return playerModelCache.stream().filter(playerModel -> playerModel.getId() == id).findFirst().get();
    }

    @Override
    public void updatePlayerModel(IPlayerModel playerModel) {
        if (playerModelCache == null) readAllPlayerModels();
        DATA_FACADE.getDataConnection().updatePlayerModel(playerModel.convert2JSON());
        playerModelCache.removeIf(plm -> plm.getId() == playerModel.getId());
        playerModelCache.add(playerModel);
        WRITER.printAction(ConsoleColors.PURPLE,"Updated",TYPE,playerModel.getId());
    }

    @Override
    public void deletePlayerModel(long id) {
        if (playerModelCache == null) readAllPlayerModels();
        DATA_FACADE.getDataConnection().deletePlayerModel(id);
        playerModelCache.removeIf(playerModel -> playerModel.getId() == id);
        WRITER.printAction(ConsoleColors.RED,"Deleted",TYPE,id);
    }
}
