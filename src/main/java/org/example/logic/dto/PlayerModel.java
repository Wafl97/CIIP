package org.example.logic.dto;

import org.example.logic.dto.interfaces.IPlayerModel;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class PlayerModel extends GenericItem<IPlayerModel> implements IPlayerModel {

    public PlayerModel(){
        super(PLAYERMODEL);
    }

    @Override
    public IPlayerModel convert2Obj(JSONObject jsonObject) {
        return (IPlayerModel) convertHelper(jsonObject);
    }

    @Override
    public IPlayerModel populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IPlayerModel) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
