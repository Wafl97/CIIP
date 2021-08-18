package org.example.logic.dto;

import org.example.logic.dto.interfaces.IGraffiti;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Graffiti extends GenericItem<IGraffiti> implements IGraffiti{

    public Graffiti(){
        super(GRAFFITI);
    }

    @Override
    public IGraffiti convert2Obj(JSONObject jsonObject) {
        return (IGraffiti) convertHelper(jsonObject);
    }

    @Override
    public IGraffiti populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IGraffiti) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
