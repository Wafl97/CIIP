package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.IGraffiti;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Graffiti extends GenericItem<IGraffiti> implements IGraffiti{

    private static final IGenericDomain GRAFFITI_DOMAIN = Domain.getInstance().getGraffitiDomain();

    public Graffiti(){
        super(GRAFFITI);
    }

    @Override
    public IGraffiti convert2Obj(JSONObject jsonObject) {
        return (IGraffiti) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable item : GRAFFITI_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IGraffiti populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IGraffiti) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
