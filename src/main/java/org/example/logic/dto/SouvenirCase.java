package org.example.logic.dto;

import org.example.logic.dto.interfaces.ISouvenirCase;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class SouvenirCase extends GenericItem<ISouvenirCase> implements ISouvenirCase {

    public SouvenirCase(){
        super(SOUVENIR);
        SUB_DOMAIN = DOMAIN.getSouvenirCaseDomain();
    }

    @Override
    public ISouvenirCase convert2Obj(JSONObject jsonObject) {
        return (ISouvenirCase) convertHelper(jsonObject);
    }

    @Override
    public ISouvenirCase populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ISouvenirCase) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
