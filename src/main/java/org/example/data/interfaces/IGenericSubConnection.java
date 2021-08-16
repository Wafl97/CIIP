package org.example.data.interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface IGenericSubConnection {

    List<JSONObject> readAll();
    boolean create(JSONObject jsonObject);
    JSONObject read(long id);
    boolean update(JSONObject jsonObject);
    boolean delete(long id);
}
