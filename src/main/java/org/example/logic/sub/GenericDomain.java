package org.example.logic.sub;

import org.example.data.GenericSubConnection;
import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGenericSubConnection;
import org.example.logic.Domain;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.IGenericDomain;
import org.example.util.Attributes;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.Attributes.*;

public final class GenericDomain implements IGenericDomain {

    private final IDataFacade DATA_FACADE;
    private final IFactory CREATOR;
    private final IActionWriter WRITER;
    private final Attributes TYPE;
    private final IGenericSubConnection SUB_CON;

    private List<Identifiable> cache;

    public GenericDomain(Attributes TYPE){
        DATA_FACADE = Domain.getInstance().getDataFacade();
        CREATOR = Domain.getInstance().getFactory();
        WRITER = Domain.getInstance().getActionWriter();
        this.TYPE = TYPE;
        switch (TYPE){
            case CAPSULE:
                SUB_CON = new GenericSubConnection(CAPSULES,CAPSULE,true);
                break;
            case CASE:
                SUB_CON = new GenericSubConnection(CASES,CASE,true);
                break;
            case GRAFFITI:
                SUB_CON = new GenericSubConnection(GRAFFITIES,GRAFFITI,true);
                break;
            case KEY:
                SUB_CON = new GenericSubConnection(KEYS,KEY,true);
                break;
            case MUSICKIT:
                SUB_CON = new GenericSubConnection(MUSICKITS,MUSICKIT,true);
                break;
            case PATCH:
                SUB_CON = new GenericSubConnection(PATCHES,PATCH,true);
                break;
            case PIN:
                SUB_CON = new GenericSubConnection(PINS,PIN,true);
                break;
            case PLAYERMODEL:
                SUB_CON = new GenericSubConnection(PLAYERMODELS,PLAYERMODEL,true);
                break;
            case SKIN:
                SUB_CON = new GenericSubConnection(SKINS,SKIN,true);
                break;
            case SOUVENIR:
                SUB_CON = new GenericSubConnection(SOUVENIRS,SOUVENIR,true);
                break;
            case STICKER:
                SUB_CON = new GenericSubConnection(STICKERS,STICKER,true);
                break;
            case TICKET:
                SUB_CON = new GenericSubConnection(TICKETS,TICKET,true);
                break;
            case VAULT:
                SUB_CON = new GenericSubConnection(VAULTS,VAULT,true);
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
        SUB_CON.create(_item.convert2JSON());
        cache.add(item);
        return true;
    }

    @Override
    public Identifiable read(long id) {
        if (cache == null) readAll();
        return cache.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    @Override
    public boolean update(Identifiable item) {
        if (cache == null) readAll();
        Convertible _item = (Convertible) item;
        SUB_CON.update(_item.convert2JSON());
        cache.removeIf(_item_ -> _item_.getId() == item.getId());
        cache.add(item);
        return true;
    }

    @Override
    public boolean delete(long id) {
        if (cache == null) readAll();
        SUB_CON.delete(id);
        cache.removeIf(item -> item.getId() == id);
        return false;
    }
}
