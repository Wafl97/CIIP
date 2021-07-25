package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.IVaultDomain;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VaultDomain implements IVaultDomain {

    private static VaultDomain instance;

    private VaultDomain(){

    }

    public static VaultDomain getInstance(){
        return instance == null ? instance = new VaultDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<IVault> vaultCache;

    @Override
    public List<IVault> readAllVaults() {
        if (vaultCache == null) {
            System.out.println("====================\nCaching Vaults");
            vaultCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllVaults()) {
                IVault newVault = CREATOR.emptyVault().convert2Obj(o);
                vaultCache.add(newVault);
            }
            System.out.println("Cache Size [" + vaultCache.size() + "]\n====================\n");
        }
        return vaultCache;
    }

    @Override
    public void createVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().createVault(vault.convert2JSON());
        vaultCache.add(vault);
    }

    @Override
    public IVault readVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        return vaultCache.stream().filter(vault -> vault.getId() == id).findFirst().get();
    }

    @Override
    public void updateVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().updateVault(vault.convert2JSON());
        vaultCache.removeIf(v -> v.getId() == vault.getId());
        vaultCache.add(vault);
    }

    @Override
    public void deleteVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().deleteVault(id);
        vaultCache.removeIf(vault -> vault.getId() == id);
    }
}
