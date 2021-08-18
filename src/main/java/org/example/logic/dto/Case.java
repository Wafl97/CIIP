package org.example.logic.dto;

import org.example.logic.dto.interfaces.ICase;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Case extends GenericItem<ICase> implements ICase{

    public Case(){
        super(CASE);
    }

    @Override
    public ICase convert2Obj(JSONObject jsonObject) {
        return (ICase) convertHelper(jsonObject);
    }

    @Override
    public ICase populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ICase) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
