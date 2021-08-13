package org.example.logic.dto;

import org.example.logic.interfaces.dto.ICapsule;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.ICapsuleDomain;
import org.example.logic.sub.CapsuleDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Capsule extends GenericItem<ICapsule> implements ICapsule {

    private static final ICapsuleDomain CAPSULE_DOMAIN = CapsuleDomain.getInstance();

    public Capsule() {
        super(CAPSULE);
    }

    @Override
    public ICapsule convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable capsule : CAPSULE_DOMAIN.readAllCapsules()) {
            if (capsule.getId() > maxValue) maxValue = capsule.getId();
        }
        return maxValue;
    }

    @Override
    public ICapsule populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
