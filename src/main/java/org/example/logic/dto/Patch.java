package org.example.logic.dto;

import org.example.logic.dto.interfaces.IPatch;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Patch extends GenericItem<IPatch> implements IPatch {

    public Patch(){
        super(PATCH);
    }

    @Override
    public IPatch convert2Obj(JSONObject jsonObject) {
        return (IPatch) convertHelper(jsonObject);
    }

    @Override
    public IPatch populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IPatch) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
