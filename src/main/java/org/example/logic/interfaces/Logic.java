package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;
import org.json.simple.JSONObject;

import java.util.List;

public interface Logic {

    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
    Factory getFactory();

    String getVersion();
    String getAppName();

    void setSelectedVault(IVault vault);
    IVault getSelectedVault();
    void setSelectedCapsule(ICapsule capsule);
    ICapsule getSelectedCapsule();

    List<ICapsule> readAllCapsules();
    void createCapsule(ICapsule capsule);
    ICapsule readCapsule(long id);
    void updateCapsule(ICapsule capsule);
    void deleteCapsule(long id);

    List<ISkin> readAllSkins();
    void createSkin(ISkin skin);
    ISkin readSkin(long id);
    void updateSkin(ISkin skin);
    void deleteSkin(long id);

    List<ISouvenirCase> readAllSouvenirCases();
    void createSouvenirCase(ISouvenirCase souvenirCase);
    ISouvenirCase readSouvenirCase(long id);
    void updateSouvenirCase(ISouvenirCase souvenirCase);
    void deleteSouvenirCase(long id);

    List<IVault> readAllVaults();
    void createVault(IVault vault);
    IVault readVault(long id);
    void updateVault(IVault vault);
    void deleteVault(long id);

    List<ISticker> readAllStickers();
    void createSticker(ISticker sticker);
    ISticker readSticker(long id);
    void updateSticker(ISticker sticker);
    void deleteSticker(long id);
}
