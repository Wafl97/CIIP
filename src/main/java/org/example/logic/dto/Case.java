package org.example.logic.dto;

import org.example.logic.interfaces.dto.ICase;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.ICaseDomain;
import org.example.logic.sub.CaseDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Case extends GenericItem<ICase> implements ICase{

    private static final ICaseDomain CASE_DOMAIN = CaseDomain.getInstance();

    public Case(){
        super(CASE);
    }

    @Override
    public ICase convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable item : CASE_DOMAIN.readAllCases()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
    }

    @Override
    public ICase populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
