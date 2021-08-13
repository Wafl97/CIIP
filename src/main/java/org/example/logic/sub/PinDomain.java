package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.IPin;
import org.example.logic.interfaces.sub.IPinDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class PinDomain implements IPinDomain {

    private static PinDomain instance;

    private PinDomain(){

    }

    public static PinDomain getInstance(){
        return instance == null ? instance = new PinDomain(): instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<IPin> pinCache;

    @Override
    public List<IPin> readAllPins() {
        if (pinCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Pins" + ConsoleColors.RESET);
            pinCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllPins()) {
                IPin newPin = CREATOR.emptyPin().convert2Obj(o);
                pinCache.add(newPin);
                System.out.println("Pin [" + ConsoleColors.BLUE + newPin.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + "Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + pinCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return pinCache;
    }

    @Override
    public void createPin(IPin pin) {
        if (pinCache == null) readAllPins();
        DATA_FACADE.getDataConnection().createPin(pin.convert2JSON());
        pinCache.add(pin);
    }

    @Override
    public IPin readPin(long id) {
        if (pinCache == null) readAllPins();
        return pinCache.stream().filter(pin -> pin.getId() == id).findFirst().get();
    }

    @Override
    public void updatePin(IPin pin) {
        if (pinCache == null) readAllPins();
        DATA_FACADE.getDataConnection().updatePin(pin.convert2JSON());
        pinCache.removeIf(p -> p.getId() == pin.getId());
        pinCache.add(pin);
    }

    @Override
    public void deletePin(long id) {
        if (pinCache == null) readAllPins();
        DATA_FACADE.getDataConnection().deletePin(id);
        pinCache.removeIf(pin -> pin.getId() == id);
    }
}
