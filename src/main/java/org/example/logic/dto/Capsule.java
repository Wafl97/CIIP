package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ICase;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Capsule extends GenericItem<ICapsule> implements ICapsule {

    private static final IGenericDomain CAPSULE_DOMAIN = Domain.getInstance().getCapsuleDomain();

    public Capsule() {
        super(CAPSULE);
    }

    @Override
    public ICapsule convert2Obj(JSONObject jsonObject) {
        return (ICapsule) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable capsule : CAPSULE_DOMAIN.readAll()) {
            if (capsule.getId() > maxValue) maxValue = capsule.getId();
        }
        return maxValue;
    }

    @Override
    public ICapsule populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ICapsule) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
