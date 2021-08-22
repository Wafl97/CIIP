package org.example.logic.dto;

import org.example.logic.dto.interfaces.IKey;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Key extends GenericItem<IKey> implements IKey {

    public Key() {
        super(KEY);
        SUB_DOMAIN = DOMAIN.getKeyDomain();
    }

    @Override
    public IKey convert2Obj(JSONObject jsonObject) {
        return (IKey) convertHelper(jsonObject);
    }

    @Override
    public IKey populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IKey) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
