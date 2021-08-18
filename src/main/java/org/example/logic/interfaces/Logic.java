package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;
import org.example.logic.dto.interfaces.*;
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

    IGenericDomain<IVault> getVaultDomain();
    IGenericDomain<ICapsule> getCapsuleDomain();
    IGenericDomain<ISticker> getStickerDomain();
    IGenericDomain<ISkin> getSkinDomain();
    IGenericDomain<ISouvenirCase> getSouvenirCaseDomain();
    IGenericDomain<IPatch> getPatchDomain();
    IGenericDomain<ICase> getCaseDomain();
    IGenericDomain<ITicket> getTicketDomain();
    IGenericDomain<IKey> getKeyDomain();
    IGenericDomain<IMusicKit> getMusicKitDomain();
    IGenericDomain<IPin> getPinDomain();
    IGenericDomain<IPlayerModel> getPlayerModelDomain();
    IGenericDomain<IGraffiti> getGraffitiDomain();

    void setSelectedVault(IVault vault);
    IVault getSelectedVault();

    List<Identifiable> readAllItems();
}
