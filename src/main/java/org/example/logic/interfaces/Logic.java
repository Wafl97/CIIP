package org.example.logic.interfaces;

import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.*;

import java.util.List;

public interface Logic {

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

    void setSelectedVault(IVault vault);
    IVault getSelectedVault();

    List<Displayable> readAllItems();
}
