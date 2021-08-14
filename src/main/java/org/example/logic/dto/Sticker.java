package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ISticker;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Sticker extends GenericItem<ISticker> implements ISticker {

    private static final IGenericDomain STICKER_DOMAIN = Domain.getInstance().getStickerDomain();

    public Sticker(){
        super(STICKER);
    }

    @Override
    public ISticker convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable sticker : STICKER_DOMAIN.readAll()){
            if (sticker.getId() > maxValue) maxValue = sticker.getId();
        }
        return maxValue;
    }

    @Override
    public ISticker populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
