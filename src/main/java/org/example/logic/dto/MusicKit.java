package org.example.logic.dto;

import org.example.logic.dto.interfaces.IMusicKit;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class MusicKit extends GenericItem<IMusicKit> implements IMusicKit{

    public MusicKit() {
        super(MUSICKIT);
        SUB_DOMAIN = DOMAIN.getMusicKitDomain();
    }

    @Override
    public IMusicKit convert2Obj(JSONObject jsonObject) {
        return (IMusicKit) convertHelper(jsonObject);
    }

    @Override
    public IMusicKit populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IMusicKit) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
