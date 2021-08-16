package org.example.logic;

import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGenericSubConnection;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.example.util.Attributes;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.ConsoleColors.*;

public final class GenericDomain implements IGenericDomain {

    private static final IDataFacade DATA_FACADE = Domain.getInstance().getDataFacade();
    private final IFactory CREATOR;
    private final IActionWriter WRITER;
    private final Attributes TYPE;
    private final IGenericSubConnection SUB_CON;

    private List<Identifiable> cache;

    public GenericDomain(Attributes TYPE){
        CREATOR = Domain.getInstance().getFactory();
        WRITER = Domain.getInstance().getActionWriter();
        this.TYPE = TYPE;
        switch (TYPE){
            case CAPSULE:
                SUB_CON = DATA_FACADE.getDataConnection().getCapsuleConnection();
                break;
            case CASE:
                SUB_CON = DATA_FACADE.getDataConnection().getCaseConnection();
                break;
            case GRAFFITI:
                SUB_CON = DATA_FACADE.getDataConnection().getGraffitiConnection();
                break;
            case KEY:
                SUB_CON = DATA_FACADE.getDataConnection().getKeyConnection();
                break;
            case MUSICKIT:
                SUB_CON = DATA_FACADE.getDataConnection().getMusicKitConnection();
                break;
            case PATCH:
                SUB_CON = DATA_FACADE.getDataConnection().getPatchConnection();
                break;
            case PIN:
                SUB_CON = DATA_FACADE.getDataConnection().getPinConnection();
                break;
            case PLAYERMODEL:
                SUB_CON = DATA_FACADE.getDataConnection().getPlayerModelConnection();
                break;
            case SKIN:
                SUB_CON = DATA_FACADE.getDataConnection().getSkinConnection();
                break;
            case SOUVENIR:
                SUB_CON = DATA_FACADE.getDataConnection().getSouvenirConnection();
                break;
            case STICKER:
                SUB_CON = DATA_FACADE.getDataConnection().getStickerConnection();
                break;
            case TICKET:
                SUB_CON = DATA_FACADE.getDataConnection().getTicketConnection();
                break;
            case VAULT:
                SUB_CON = DATA_FACADE.getDataConnection().getVaultConnection();
                break;
            default:
                SUB_CON = null;
                break;
        }
    }

    @Override
    public List<Identifiable> readAll() {
        if (cache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching type: " + TYPE.toNorm() + ConsoleColors.RESET);
            cache = new ArrayList<>();
            for (JSONObject o : SUB_CON.readAll()){
                Convertible item = (Convertible) CREATOR.makeNew(TYPE).convert2Obj(o);
                cache.add((Identifiable) item);
                System.out.println(TYPE.toNorm() + ConsoleColors.GREEN +  " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + cache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return cache;
    }

    @Override
    public boolean create(Identifiable item) {
        if (cache == null) readAll();
        Convertible _item = (Convertible) item;
        cache.add(item);
        WRITER.printAction(GREEN,"CREATED",TYPE.toNorm(),item.getId(),true);
        return SUB_CON.create(_item.convert2JSON());
    }

    @Override
    public Identifiable read(long id) {
        if (cache == null) readAll();
        WRITER.printAction(YELLOW,"READ",TYPE.toNorm(),id,false);
        return cache.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    @Override
    public boolean update(Identifiable item) {
        if (cache == null) readAll();
        Convertible _item = (Convertible) item;
        cache.removeIf(_item_ -> _item_.getId() == item.getId());
        cache.add(item);
        WRITER.printAction(PURPLE,"UPDATED",TYPE.toNorm(),item.getId(),true);
        return SUB_CON.update(_item.convert2JSON());
    }

    @Override
    public boolean delete(long id) {
        if (cache == null) readAll();
        cache.removeIf(item -> item.getId() == id);
        WRITER.printAction(RED,"DELETED",TYPE.toNorm(),id,true);
        return SUB_CON.delete(id);
    }
}
