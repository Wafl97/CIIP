package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.ActionWriter;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.ISouvenirCase;
import org.example.logic.interfaces.sub.ISouvenirCaseDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class SouvenirCaseDomain implements ISouvenirCaseDomain {

    private static SouvenirCaseDomain instance;

    private SouvenirCaseDomain(){

    }

    public static SouvenirCaseDomain getInstance(){
        return instance == null ? instance = new SouvenirCaseDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();
    private static final IActionWriter WRITER = new ActionWriter();
    private static final String TYPE = "Souvenir";

    private List<ISouvenirCase> souvenirCaseCache;

    @Override
    public List<ISouvenirCase> readAllSouvenirCases() {
        if (souvenirCaseCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching SouvenirCases" + ConsoleColors.RESET);
            souvenirCaseCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllSouvenirs()){
                ISouvenirCase newSouvenir = CREATOR.emptySouvenirCase().convert2Obj(o);
                souvenirCaseCache.add(newSouvenir);
                System.out.println("SouvenirCase [" + ConsoleColors.BLUE + newSouvenir.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + souvenirCaseCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return souvenirCaseCache;
    }

    @Override
    public void createSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().createSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.add(souvenirCase);
        WRITER.printAction(ConsoleColors.GREEN,"Created",TYPE,souvenirCase.getId(),true);
    }

    @Override
    public ISouvenirCase readSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        WRITER.printAction(ConsoleColors.YELLOW,"Read",TYPE,id,false);
        return souvenirCaseCache.stream().filter(souvenir -> souvenir.getId() == id).findFirst().get();
    }

    @Override
    public void updateSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().updateSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.removeIf(svn -> svn.getId() == souvenirCase.getId());
        souvenirCaseCache.add(souvenirCase);
        WRITER.printAction(ConsoleColors.PURPLE,"Updated",TYPE,souvenirCase.getId(),true);
    }

    @Override
    public void deleteSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().deleteSouvenir(id);
        souvenirCaseCache.removeIf(souvenirCase -> souvenirCase.getId() == id);
        WRITER.printAction(ConsoleColors.RED,"Deleted",TYPE,id,true);
    }
}
