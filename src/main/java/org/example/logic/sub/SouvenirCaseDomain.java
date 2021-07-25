package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ISouvenirCase;
import org.example.logic.interfaces.sub.ISouvenirCaseDomain;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SouvenirCaseDomain implements ISouvenirCaseDomain {

    private static SouvenirCaseDomain instance;

    private SouvenirCaseDomain(){

    }

    public static SouvenirCaseDomain getInstance(){
        return instance == null ? instance = new SouvenirCaseDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ISouvenirCase> souvenirCaseCache;

    @Override
    public List<ISouvenirCase> readAllSouvenirCases() {
        if (souvenirCaseCache == null){
            System.out.println("===========================================\nCaching SouvenirCases");
            souvenirCaseCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllSouvenirs()){
                ISouvenirCase newSouvenir = CREATOR.emptySouvenirCase().convert2Obj(o);
                souvenirCaseCache.add(newSouvenir);
                System.out.println("SouvenirCase [" + newSouvenir.getName() + "] Cached");
            }
            System.out.println("Cache Size [" + souvenirCaseCache.size() + "]\n===========================================\n");
        }
        return souvenirCaseCache;
    }

    @Override
    public void createSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().createSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.add(souvenirCase);
    }

    @Override
    public ISouvenirCase readSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        return souvenirCaseCache.stream().filter(souvenir -> souvenir.getId() == id).findFirst().get();
    }

    @Override
    public void updateSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().updateSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.removeIf(svn -> svn.getId() == souvenirCase.getId());
        souvenirCaseCache.add(souvenirCase);
    }

    @Override
    public void deleteSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().deleteSouvenir(id);
        souvenirCaseCache.removeIf(souvenirCase -> souvenirCase.getId() == id);
    }
}
