package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ISouvenirCase;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class SouvenirCase extends GenericItem<ISouvenirCase> implements ISouvenirCase {

    private static final IGenericDomain SOUVENIR_CASE_DOMAIN = Domain.getInstance().getSouvenirCaseDomain();

    public SouvenirCase(){
        super(SOUVENIR);
    }

    @Override
    public ISouvenirCase convert2Obj(JSONObject jsonObject) {
        return (ISouvenirCase) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable souvenirCase : SOUVENIR_CASE_DOMAIN.readAll()) {
            if (souvenirCase.getId() > maxValue) maxValue = souvenirCase.getId();
        }
        return maxValue;
    }

    @Override
    public ISouvenirCase populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ISouvenirCase) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
