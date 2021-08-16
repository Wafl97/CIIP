package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.IKey;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Key extends GenericItem<IKey> implements IKey {

    private static final IGenericDomain KEY_DOMAIN = Domain.getInstance().getKeyDomain();

    public Key() {
        super(KEY);
    }

    @Override
    public IKey convert2Obj(JSONObject jsonObject) {
        return (IKey) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable item : KEY_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IKey populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IKey) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
