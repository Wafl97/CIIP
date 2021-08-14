package org.example.data.interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface IGenericSubConnection {

    List<JSONObject> readAll();
    void create(JSONObject jsonObject);
    JSONObject read(long id);
    void update(JSONObject jsonObject);
    void delete(long id);
}
