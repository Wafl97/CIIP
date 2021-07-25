package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IVault;

import java.util.List;

public interface IVaultDomain {

    List<IVault> readAllVaults();
    void createVault(IVault vault);
    IVault readVault(long id);
    void updateVault(IVault vault);
    void deleteVault(long id);
}
