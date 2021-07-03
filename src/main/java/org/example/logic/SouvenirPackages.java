package org.example.logic;

import org.example.logic.Interfaces.Identifiable;
import org.json.simple.JSONObject;

public class SouvenirPackages extends Item<SouvenirPackages> implements Identifiable {

    @Override
    public void updateCurrPrice() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public JSONObject convert2JSON() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public SouvenirPackages convert2Obj(JSONObject jsonObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public long findMaxID() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
