package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
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
        return (ISticker) convertHelper(jsonObject);
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
    public ISticker populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ISticker) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
