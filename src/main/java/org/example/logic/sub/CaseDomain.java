package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ICapsule;
import org.example.logic.interfaces.dto.ICase;
import org.example.logic.interfaces.sub.ICaseDomain;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CaseDomain implements ICaseDomain {

    private static CaseDomain instance;

    private CaseDomain(){

    }

    public static CaseDomain getInstance(){
        return instance == null ? instance = new CaseDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ICase> casesCache;

    @Override
    public List<ICase> readAllCases() {
        if (casesCache == null){
            System.out.println("===========================================\nCaching Cases");
            casesCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllCases()){
                ICase newCase = CREATOR.emptyCase().convert2Obj(o);
                casesCache.add(newCase);
                System.out.println("Case [" + newCase.getName() + "] Cached");
            }
            System.out.println("Cache Size [" + casesCache.size() + "]\n===========================================\n");
        }
        return casesCache;
    }

    @Override
    public void createCase(ICase Case) {
        if (casesCache == null) readAllCases();
        DATA_FACADE.getDataConnection().createCase(Case.convert2JSON());
        casesCache.add(Case);
    }

    @Override
    public ICase readCase(long id) {
        if (casesCache == null) readAllCases();
        return casesCache.stream().filter(Case -> Case.getId() == id).findFirst().get();
    }

    @Override
    public void updateCase(ICase Case) {
        if (casesCache == null) readAllCases();
        DATA_FACADE.getDataConnection().updateCase(Case.convert2JSON());
        casesCache.removeIf(cse -> cse.getId() == Case.getId());
        casesCache.add(Case);
    }

    @Override
    public void deleteCase(long id) {
        if (casesCache == null) readAllCases();
        DATA_FACADE.getDataConnection().deleteCase(id);
        casesCache.removeIf(Case -> Case.getId() == id);
    }
}
