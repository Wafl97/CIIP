package org.example.logic;

import org.example.logic.interfaces.ISticker;
import org.example.logic.interfaces.comps.Identifiable;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Sticker extends GenericItem<ISticker> implements ISticker {

    public Sticker(){
        super(STICKER);
    }

    @Override
    public ISticker convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(STICKER.toString());
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
        for (Identifiable sticker : Domain.getInstance().readAllStickers()){
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
        updateCurrPrice();
        return this;
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "id=" + getId() +
                ", initPrice=" + getInitPrice() +
                ", currPrice=" + getCurrPrice() +
                ", name='" + getName() + '\'' +
                ", image='" + getImage() + '\'' +
                ", link='" + getStashLink() + '\'' +
                '}';
    }
}
