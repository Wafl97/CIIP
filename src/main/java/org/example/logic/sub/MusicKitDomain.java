package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.ActionWriter;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.IMusicKit;
import org.example.logic.interfaces.sub.IMusicKitDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MusicKitDomain implements IMusicKitDomain {

    private static MusicKitDomain instance;

    private MusicKitDomain(){

    }

    public static MusicKitDomain getInstance(){
        return instance == null ? instance = new MusicKitDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();
    private static final IActionWriter WRITER = new ActionWriter();
    private static final String TYPE = "MusicKit";

    private List<IMusicKit> musicKitCache;

    @Override
    public List<IMusicKit> readAllMusicKits() {
        if (musicKitCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching MusicKits" + ConsoleColors.RESET);
            musicKitCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllMusicKits()) {
                IMusicKit newMusicKit = CREATOR.emptyMusicKit().convert2Obj(o);
                musicKitCache.add(newMusicKit);
                System.out.println("MusicKit [" + ConsoleColors.BLUE + newMusicKit.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + "Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + musicKitCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return musicKitCache;
    }

    @Override
    public void createMusicKit(IMusicKit musicKit) {
        if (musicKitCache == null) readAllMusicKits();
        DATA_FACADE.getDataConnection().createMusicKit(musicKit.convert2JSON());
        musicKitCache.add(musicKit);
        WRITER.printAction(ConsoleColors.GREEN,"Created",TYPE,musicKit.getId(),true);
    }

    @Override
    public IMusicKit readMusicKit(long id) {
        if (musicKitCache == null) readAllMusicKits();
        WRITER.printAction(ConsoleColors.YELLOW,"Read",TYPE,id,false);
        return musicKitCache.stream().filter(musicKit -> musicKit.getId() == id).findFirst().get();
    }

    @Override
    public void updateMusicKit(IMusicKit musicKit) {
        if (musicKitCache == null) readAllMusicKits();
        DATA_FACADE.getDataConnection().updateMusicKit(musicKit.convert2JSON());
        musicKitCache.removeIf(muk -> muk.getId() == musicKit.getId());
        musicKitCache.add(musicKit);
        WRITER.printAction(ConsoleColors.PURPLE,"Updated",TYPE,musicKit.getId(),true);
    }

    @Override
    public void deleteMusicKit(long id) {
        if (musicKitCache == null) readAllMusicKits();
        DATA_FACADE.getDataConnection().deleteMusicKit(id);
        musicKitCache.removeIf(musicKit -> musicKit.getId() == id);
        WRITER.printAction(ConsoleColors.RED,"Deleted",TYPE,id,true);
    }
}
