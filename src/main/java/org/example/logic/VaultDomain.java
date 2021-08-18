package org.example.logic;

import org.example.logic.dto.interfaces.IVault;
import org.example.util.Attributes;

public final class VaultDomain extends GenericDomain<IVault> {

    public VaultDomain(){
        super(Attributes.VAULT);
        SUB_CON = DATA_FACADE.getDataConnection().getVaultConnection();
    }
}
