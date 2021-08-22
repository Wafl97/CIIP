package org.example.logic.dto;

import org.example.logic.dto.interfaces.ISticker;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Sticker extends GenericItem<ISticker> implements ISticker {

    public Sticker(){
        super(STICKER);
        SUB_DOMAIN = DOMAIN.getStickerDomain();
    }

    @Override
    public ISticker convert2Obj(JSONObject jsonObject) {
        return (ISticker) convertHelper(jsonObject);
    }

    @Override
    public ISticker populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ISticker) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
