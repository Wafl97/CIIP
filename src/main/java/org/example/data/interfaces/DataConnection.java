package org.example.data.interfaces;

import org.json.simple.JSONObject;

import java.util.List;

public interface DataConnection {

    boolean connect(boolean print);

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

    List<JSONObject> readAllStickers();
    void createSticker(JSONObject jsonObject);
    JSONObject readSticker(long id);
    void updateSticker(JSONObject jsonObject);
    void deleteSticker(long id);

    List<JSONObject> readAllPatches();
    void createPatch(JSONObject jsonObject);
    JSONObject readPatch(long id);
    void updatePatch(JSONObject jsonObject);
    void deletePatch(long id);

    List<JSONObject> readAllCases();
    void createCase(JSONObject jsonObject);
    JSONObject readCase(long id);
    void updateCase(JSONObject jsonObject);
    void deleteCase(long id);

    List<JSONObject> readAllTickets();
    void createTicket(JSONObject jsonObject);
    JSONObject readTicket(long id);
    void updateTicket(JSONObject jsonObject);
    void deleteTicket(long id);

    List<JSONObject> readAllKeys();
    void createKey(JSONObject jsonObject);
    JSONObject readKey(long id);
    void updateKey(JSONObject jsonObject);
    void deleteKey(long id);

    List<JSONObject> readAllMusicKits();
    void createMusicKit(JSONObject jsonObject);
    JSONObject readMusicKit(long id);
    void updateMusicKit(JSONObject jsonObject);
    void deleteMusicKit(long id);

    List<JSONObject> readAllPins();
    void createPin(JSONObject jsonObject);
    JSONObject readPin(long id);
    void updatePin(JSONObject jsonObject);
    void deletePin(long id);

    List<JSONObject> readAllPlayerModels();
    void createPlayerModel(JSONObject jsonObject);
    JSONObject readPlayerModel(long id);
    void updatePlayerModel(JSONObject jsonObject);
    void deletePlayerModel(long id);

    List<JSONObject> readAllGraffities();
    void createGraffiti(JSONObject jsonObject);
    JSONObject readGraffiti(long id);
    void updateGraffiti(JSONObject jsonObject);
    void deleteGraffiti(long id);
}
