package org.example.data.Interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface DataConnection {

    List<JSONObject> readAllVaults();
    void createVault(JSONObject jsonObject);
    JSONObject readVault(long id);
    void updateVault(JSONObject jsonObject);
    void deleteVault(long id);

    List<JSONObject> readAllCapsules();
    void createCapsule(JSONObject jsonObject);
    JSONObject readCapsule(long id);
    void updateCapsule(JSONObject jsonObject);
    void deleteCapsule(long id);

    List<JSONObject> readAllSouvenirs();
    void createSouvenir(JSONObject jsonObject);
    JSONObject readSouvenir(long id);
    void updateSouvenir(JSONObject jsonObject);
    void deleteSouvenir(long id);

    List<JSONObject> readAllSkins();
    void createSkin(JSONObject jsonObject);
    JSONObject readSkin(long id);
    void updateSkin(JSONObject jsonObject);
    void deleteSKin(long id);
}
