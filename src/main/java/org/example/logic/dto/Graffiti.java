package org.example.logic.dto;

import org.example.logic.interfaces.dto.IGraffiti;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.IGraffitiDomain;
import org.example.logic.sub.GraffitiDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Graffiti extends GenericItem<IGraffiti> implements IGraffiti{

    private static final IGraffitiDomain GRAFFITI_DOMAIN = GraffitiDomain.getInstance();

    public Graffiti(){
        super(GRAFFITI);
    }

    @Override
    public IGraffiti convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(jsonAttribute);
        return  populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString())
        );
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable item : GRAFFITI_DOMAIN.readAllGraffities()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IGraffiti populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
