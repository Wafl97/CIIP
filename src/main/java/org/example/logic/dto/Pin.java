package org.example.logic.dto;

import org.example.logic.interfaces.dto.IPin;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Pin extends GenericItem<IPin> implements IPin{



    public Pin(){
        super(PIN);
    }

    @Override
    public IPin convert2Obj(JSONObject jsonObject) {
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

        return maxValue;
    }

    @Override
    public IPin populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
