package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ISticker;
import org.example.logic.interfaces.sub.IStickerDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class StickerDomain implements IStickerDomain {

    private static StickerDomain instance;

    private StickerDomain(){

    }

    public static StickerDomain getInstance(){
        return instance == null ? instance = new StickerDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ISticker> stickerCache;

    @Override
    public List<ISticker> readAllStickers() {
        if (stickerCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Stickers" + ConsoleColors.RESET);
            stickerCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllStickers()){
                ISticker newSticker = CREATOR.emptySticker().convert2Obj(o);
                stickerCache.add(newSticker);
                System.out.println("Sticker [" + ConsoleColors.BLUE + newSticker.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + stickerCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return stickerCache;
    }

    @Override
    public void createSticker(ISticker sticker) {
        if (stickerCache == null) readAllStickers();
        stickerCache.add(sticker);
    }

    @Override
    public ISticker readSticker(long id) {
        if (stickerCache == null) readAllStickers();
        return stickerCache.stream().filter(sticker -> sticker.getId() == id).findFirst().get();
    }

    @Override
    public void updateSticker(ISticker sticker) {
        if (stickerCache == null) readAllStickers();
        DATA_FACADE.getDataConnection().updateSticker(sticker.convert2JSON());
        stickerCache.removeIf(stk -> stk.getId() == sticker.getId());
        stickerCache.add(sticker);
    }

    @Override
    public void deleteSticker(long id) {
        if (stickerCache == null) readAllStickers();
        DATA_FACADE.getDataConnection().deleteSticker(id);
        stickerCache.removeIf(sticker -> sticker.getId() == id);
    }
}
