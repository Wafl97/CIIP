package org.example.logic.dto;

import org.example.logic.interfaces.dto.IPin;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.IPinDomain;
import org.example.logic.sub.PinDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Pin extends GenericItem<IPin> implements IPin{

    private static final IPinDomain PIN_DOMAIN = PinDomain.getInstance();

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
        for (Identifiable item : PIN_DOMAIN.readAllPins()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
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
