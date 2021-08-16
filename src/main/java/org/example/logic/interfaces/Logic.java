package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;
import org.example.logic.dto.interfaces.IVault;
import org.example.logic.dto.interfaces.comps.Identifiable;

import java.util.List;

public interface Logic {

    void init(boolean print, boolean cacheItems,boolean loadGFX);

    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
    IFactory getFactory();
    IActionWriter getActionWriter();

    String getVersion();
    String getAppName();

    IGenericDomain getVaultDomain();
    IGenericDomain getCapsuleDomain();
    IGenericDomain getStickerDomain();
    IGenericDomain getSkinDomain();
    IGenericDomain getSouvenirCaseDomain();
    IGenericDomain getPatchDomain();
    IGenericDomain getCaseDomain();
    IGenericDomain getTicketDomain();
    IGenericDomain getKeyDomain();
    IGenericDomain getMusicKitDomain();
    IGenericDomain getPinDomain();
    IGenericDomain getPlayerModelDomain();
    IGenericDomain getGraffitiDomain();

    void setSelectedVault(IVault vault);
    IVault getSelectedVault();

    List<Identifiable> readAllItems();
}
