package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.interfaces.dto.IPlayerModel;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class PlayerModel extends GenericItem<IPlayerModel> implements IPlayerModel {

    private static final IGenericDomain PLAYER_MODEL_DOMAIN = Domain.getInstance().getPlayerModelDomain();

    public PlayerModel(){
        super(PLAYERMODEL);
    }

    @Override
    public IPlayerModel convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable item : PLAYER_MODEL_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IPlayerModel populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
