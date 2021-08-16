package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.IPlayerModel;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class PlayerModel extends GenericItem<IPlayerModel> implements IPlayerModel {

    private static final IGenericDomain PLAYER_MODEL_DOMAIN = Domain.getInstance().getPlayerModelDomain();

    public PlayerModel(){
        super(PLAYERMODEL);
    }

    @Override
    public IPlayerModel convert2Obj(JSONObject jsonObject) {
        return (IPlayerModel) convertHelper(jsonObject);
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
    public IPlayerModel populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IPlayerModel) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
