package org.example.logic.dto;

import org.example.logic.interfaces.dto.ISouvenirCase;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.ISouvenirCaseDomain;
import org.example.logic.sub.SouvenirCaseDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class SouvenirCase extends GenericItem<ISouvenirCase> implements ISouvenirCase {

    private static final ISouvenirCaseDomain SOUVENIR_CASE_DOMAIN = SouvenirCaseDomain.getInstance();

    public SouvenirCase(){
        super(SOUVENIR);
    }

    @Override
    public ISouvenirCase convert2Obj(JSONObject jsonObject) {
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
        for (Identifiable souvenirCase : SOUVENIR_CASE_DOMAIN.readAllSouvenirCases()) {
            if (souvenirCase.getId() > maxValue) maxValue = souvenirCase.getId();
        }
        return maxValue;
    }

    @Override
    public ISouvenirCase populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }

    @Override
    public String toString() {
        return "SouvenirCase{" +
                "id=" + getId() +
                ", initPrice=" + getInitPrice() +
                ", currPrice=" + getCurrPrice() +
                ", name='" + getName() + '\'' +
                ", image='" + getImage() + '\'' +
                ", link='" + getStashLink() + '\'' +
                '}';
    }
}
