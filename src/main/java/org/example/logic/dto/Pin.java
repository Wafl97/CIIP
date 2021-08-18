package org.example.logic.dto;

import org.example.logic.dto.interfaces.IPin;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Pin extends GenericItem<IPin> implements IPin{

    public Pin(){
        super(PIN);
    }

    @Override
    public IPin convert2Obj(JSONObject jsonObject) {
        return (IPin) convertHelper(jsonObject);
    }

    @Override
    public IPin populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IPin) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
