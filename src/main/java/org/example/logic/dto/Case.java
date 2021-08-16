package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICase;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Case extends GenericItem<ICase> implements ICase{

    private static final IGenericDomain CASE_DOMAIN = Domain.getInstance().getCaseDomain();

    public Case(){
        super(CASE);
    }

    @Override
    public ICase convert2Obj(JSONObject jsonObject) {
        return (ICase) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable item : CASE_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public ICase populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ICase) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
