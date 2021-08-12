package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.IKey;
import org.example.logic.interfaces.sub.IKeyDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class KeyDomain implements IKeyDomain {

    private static KeyDomain instance;

    private KeyDomain(){

    }

    public static KeyDomain getInstance(){
        return instance == null ? instance = new KeyDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<IKey> keyCache;

    @Override
    public List<IKey> readAllKeys() {
        if (keyCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Keys" + ConsoleColors.RESET);
            keyCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllKeys()){
                IKey newKey = CREATOR.emptyKey().convert2Obj(o);
                keyCache.add(newKey);
                System.out.println("Key [" + ConsoleColors.BLUE + newKey.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + keyCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return keyCache;
    }

    @Override
    public void createKey(IKey key) {
        if (keyCache == null) readAllKeys();
        DATA_FACADE.getDataConnection().createKey(key.convert2JSON());
        keyCache.add(key);
    }

    @Override
    public IKey readKey(long id) {
        if (keyCache == null) readAllKeys();
        return keyCache.stream().filter(key -> key.getId() == id).findFirst().get();
    }

    @Override
    public void updateKey(IKey key) {
        if (keyCache == null) readAllKeys();
        DATA_FACADE.getDataConnection().updateKey(key.convert2JSON());
        keyCache.removeIf(k -> k.getId() == key.getId());
        keyCache.add(key);
    }

    @Override
    public void deleteKey(long id) {
        if (keyCache == null) readAllKeys();
        DATA_FACADE.getDataConnection().deleteKey(id);
        keyCache.removeIf(key -> key.getId() == id);
    }
}
