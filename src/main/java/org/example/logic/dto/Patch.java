package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.IPatch;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Patch extends GenericItem<IPatch> implements IPatch {

    private static final IGenericDomain PATCH_DOMAIN = Domain.getInstance().getPatchDomain();

    public Patch(){
        super(PATCH);
    }

    @Override
    public IPatch convert2Obj(JSONObject jsonObject) {
        return (IPatch) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable patch : PATCH_DOMAIN.readAll()){
            if (patch.getId() > maxValue) maxValue = patch.getId();
        }
        return maxValue;
    }

    @Override
    public IPatch populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (IPatch) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
