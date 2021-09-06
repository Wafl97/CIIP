package org.example.logic;

import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGenericSubConnection;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.IGenericDomain;
import org.example.util.Attributes;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.ConsoleColors.*;

public abstract class GenericDomain<T> implements IGenericDomain<T> {

    protected static final IDataFacade DATA_FACADE = Domain.getInstance().getDataFacade();
    protected final IFactory CREATOR;
    protected final IActionWriter WRITER;
    protected final Attributes TYPE;
    protected IGenericSubConnection SUB_CON;

    protected List<Transferable<T>> cache;


    public GenericDomain(Attributes TYPE){
        CREATOR = Domain.getInstance().getFactory();
        WRITER = Domain.getInstance().getActionWriter();
        SUB_CON = DATA_FACADE.getDataConnection().getVaultConnection();
        this.TYPE = TYPE;
    }

    @Override
    public String getType() {
        return TYPE.toNorm();
    }

    @Override
    public int cacheSize() {
        return cache.size();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Transferable<T>> readAll() {
        if (cache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching type: " + TYPE.toNorm() + ConsoleColors.RESET);
            cache = new ArrayList<>();
            for (JSONObject o : SUB_CON.readAll()){
                Transferable<T> item = (Transferable<T>) CREATOR.makeNew(TYPE).convert2Obj(o);
                cache.add(item);
                System.out.println(TYPE.toNorm() + ConsoleColors.GREEN +  " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + cache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return cache;
    }

    @Override
    public boolean create(Transferable<T> item) {
        if (cache == null) readAll();
        cache.add(item);
        WRITER.printAction(GREEN,"CREATED",TYPE.toNorm(),item.getId(),true);
        return SUB_CON.create(item.convert2JSON());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Transferable<T> read(long id) {
        if (cache == null) readAll();
        WRITER.printAction(YELLOW,"READ",TYPE.toNorm(),id,false);
        boolean b = cache.stream().anyMatch(item -> item.getId() == id);
        System.out.println("DEBUG :: item found " + b);
        return cache.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    @Override
    public boolean update(Transferable<T> item) {
        if (cache == null) readAll();
        cache.removeIf(_item_ -> _item_.getId() == item.getId());
        cache.add(item);
        WRITER.printAction(PURPLE,"UPDATED",TYPE.toNorm(),item.getId(),true);
        return SUB_CON.update(item.convert2JSON());
    }

    @Override
    public boolean delete(long id) {
        if (cache == null) readAll();
        cache.removeIf(item -> item.getId() == id);
        WRITER.printAction(RED,"DELETED",TYPE.toNorm(),id,true);
        return SUB_CON.delete(id);
    }
}
