package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.IMusicKit;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class MusicKit extends GenericItem<IMusicKit> implements IMusicKit{

    private static final IGenericDomain MUSIC_KIT_DOMAIN = Domain.getInstance().getMusicKitDomain();

    public MusicKit() {
        super(MUSICKIT);
    }

    @Override
    public IMusicKit convert2Obj(JSONObject jsonObject) {
        return (IMusicKit) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable item : MUSIC_KIT_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public IMusicKit populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IMusicKit) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
