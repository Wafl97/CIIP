package org.example.logic;

import org.example.logic.Interfaces.Identifiable;
import org.json.simple.JSONObject;

// FIXME: 04-07-2021 
public class SouvenirPackages extends Item<SouvenirPackages> implements Identifiable {

    // FIXME: 04-07-2021 
    @Override
    public void updateCurrPrice() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 04-07-2021 
    @Override
    public JSONObject convert2JSON() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 04-07-2021 
    @Override
    public SouvenirPackages convert2Obj(JSONObject jsonObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 04-07-2021 
    @Override
    public long findMaxID() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
