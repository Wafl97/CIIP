package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.ActionWriter;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.IActionWriter;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.IVaultDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class VaultDomain implements IVaultDomain {

    private static VaultDomain instance;

    private VaultDomain(){

    }

    public static VaultDomain getInstance(){
        return instance == null ? instance = new VaultDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();
    private static final IActionWriter WRITER = new ActionWriter();
    private static final String TYPE = "Vault";

    private List<IVault> vaultCache;

    @Override
    public List<IVault> readAllVaults() {
        if (vaultCache == null) {
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Vaults" + ConsoleColors.RESET);
            vaultCache = new ArrayList<>();
            byte b = 0;
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllVaults()) {
                IVault newVault = CREATOR.emptyVault().convert2Obj(o);
                vaultCache.add(newVault);
                System.out.println("Vault [" + ConsoleColors.BLUE + newVault.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + vaultCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return vaultCache;
    }

    @Override
    public void createVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().createVault(vault.convert2JSON());
        vaultCache.add(vault);
        WRITER.printAction(ConsoleColors.GREEN,"Created",TYPE,vault.getId());
    }

    @Override
    public IVault readVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        WRITER.printAction(ConsoleColors.YELLOW,"Read",TYPE,id);
        return vaultCache.stream().filter(vault -> vault.getId() == id).findFirst().get();
    }

    @Override
    public void updateVault(IVault vault) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().updateVault(vault.convert2JSON());
        vaultCache.removeIf(v -> v.getId() == vault.getId());
        vaultCache.add(vault);
        WRITER.printAction(ConsoleColors.PURPLE,"Updated",TYPE,vault.getId());
    }

    @Override
    public void deleteVault(long id) {
        if (vaultCache == null) this.readAllVaults();
        DATA_FACADE.getDataConnection().deleteVault(id);
        vaultCache.removeIf(vault -> vault.getId() == id);
        WRITER.printAction(ConsoleColors.RED,"Deleted",TYPE,id);
    }
}
