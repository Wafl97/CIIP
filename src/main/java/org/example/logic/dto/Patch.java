package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.IPatch;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Patch extends GenericItem<IPatch> implements IPatch {

    private static final IGenericDomain PATCH_DOMAIN = Domain.getInstance().getPatchDomain();

    public Patch(){
        super(PATCH);
    }

    @Override
    public IPatch convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable patch : PATCH_DOMAIN.readAll()){
            if (patch.getId() > maxValue) maxValue = patch.getId();
        }
        return maxValue;
    }

    @Override
    public IPatch populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
