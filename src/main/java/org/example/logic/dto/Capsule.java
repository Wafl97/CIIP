package org.example.logic.dto;

import org.example.logic.dto.interfaces.ICapsule;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Capsule extends GenericItem<ICapsule> implements ICapsule {

    public Capsule() {
        super(CAPSULE);
    }

    @Override
    public ICapsule convert2Obj(JSONObject jsonObject) {
        return (ICapsule) convertHelper(jsonObject);
    }

    @Override
    public ICapsule populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ICapsule) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
