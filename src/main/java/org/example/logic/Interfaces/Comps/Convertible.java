package org.example.logic.Interfaces.Comps;

import org.json.simple.JSONObject;

public interface Convertible<T> {

    JSONObject convert2JSON();

    T convert2Obj(JSONObject jsonObject);
}
