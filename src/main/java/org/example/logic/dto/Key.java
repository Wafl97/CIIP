package org.example.logic.dto;

import org.example.logic.interfaces.dto.IKey;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.IKeyDomain;
import org.example.logic.sub.KeyDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Key extends GenericItem<IKey> implements IKey {

    private static final IKeyDomain KEY_DOMAIN = KeyDomain.getInstance();

    public Key() {
        super(KEY);
    }

    @Override
    public IKey convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable item : KEY_DOMAIN.readAllKeys()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IKey populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
