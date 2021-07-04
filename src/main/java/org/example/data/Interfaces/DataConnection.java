package org.example.data.Interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface DataConnection {

    List<JSONObject> readAllInvestments();
    void createInvestment(JSONObject jsonObject);
    JSONObject readInvestment(long id);
    void updateInvestment(JSONObject jsonObject);
    void deleteInvestment(long id);

    List<JSONObject> readAllCapsules();
    void createCapsule(JSONObject jsonObject);
    JSONObject readCapsule(long id);
    void updateCapsule(JSONObject jsonObject);
    void deleteCapsule(long id);

}
