package org.example.logic;

import org.example.logic.interfaces.ICapsule;
import org.example.logic.interfaces.comps.Identifiable;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Capsule extends GenericItem<ICapsule> implements ICapsule {

    public Capsule() {
        super(CAPSULE);
    }

    @Override
    public ICapsule convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(CAPSULE.toString());
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
        for (Identifiable capsule : Domain.getInstance().readAllCapsules()) {
            if (capsule.getId() > maxValue) maxValue = capsule.getId();
        }
        return maxValue;
    }

    @Override
    public ICapsule populate(long id, double initPrice, String name, String image, String stashLink) {
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
        return "Capsule{" +
                "id=" + getId() +
                ", initPrice=" + getInitPrice() +
                ", currPrice=" + getCurrPrice() +
                ", name='" + getName() + '\'' +
                ", image='" + getImage() + '\'' +
                ", link='" + getStashLink() + '\'' +
                '}';
    }
}
