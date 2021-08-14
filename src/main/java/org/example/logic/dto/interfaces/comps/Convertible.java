package org.example.logic.dto.interfaces.comps;

import org.json.simple.JSONObject;

public interface Convertible<T> {

    JSONObject convert2JSON();

    T convert2Obj(JSONObject jsonObject);
}
