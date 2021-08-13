package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.dto.IMusicKit;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.*;

import java.util.List;

public interface Logic {

    void init();

    IDataFacade getDataFacade();
    IFileHandler getFileHandler();
    Factory getFactory();

    String getVersion();
    String getAppName();

    IVaultDomain getVaultDomain();
    ICapsuleDomain getCapsuleDomain();
    IStickerDomain getStickerDomain();
    ISkinDomain getSkinDomain();
    ISouvenirCaseDomain getSouvenirCaseDomain();
    IPatchDomain getPatchDomain();
    ICaseDomain getCaseDomain();
    ITicketDomain getTicketDomain();
    IKeyDomain getKeyDomain();
    IMusicKitDomain getMusicKitDomain();
    IPinDomain getPinDomain();

    void setSelectedVault(IVault vault);
    IVault getSelectedVault();

    List<Displayable> readAllItems();
}
