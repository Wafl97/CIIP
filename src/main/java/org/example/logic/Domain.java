package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.*;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.*;
import org.example.logic.sub.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private final IDataFacade DATA_FACADE;
    private final Factory CREATOR;
    private final IFileHandler FILE_HANDLER;

    private static final IVaultDomain VAULT_DOMAIN = VaultDomain.getInstance();
    private static final ICapsuleDomain CAPSULE_DOMAIN = CapsuleDomain.getInstance();
    private static final ISkinDomain SKIN_DOMAIN = SkinDomain.getInstance();
    private static final ISouvenirCaseDomain SOUVENIR_CASE_DOMAIN = SouvenirCaseDomain.getInstance();
    private static final IStickerDomain STICKER_DOMAIN = StickerDomain.getInstance();

    private IVault selectedVault;

    private static final String APP_NAME = "CIIP";
    private static final String VERSION;
    static {
        String s;
        try {
            Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
            process.waitFor();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            s = reader.readLine();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            s = "UNKNOWN";
        }
        VERSION = s;
    }

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){
        System.out.println("||======================================||");
        System.out.println("||\t\t\tStarting " + getAppName() + " - " + getVersion() + "\t\t\t||");
        System.out.println("||======================================||");

        System.out.println("\t - Getting Factory");
        CREATOR = StructureCreator.getInstance();
        System.out.println("\t - Factory type: [" + CREATOR.getClass().getSimpleName() + "]");

        System.out.println("\t - Getting FileHandler");
        FILE_HANDLER = FileHandler.getInstance();

        System.out.println("\t - Getting DataFacade");
        DATA_FACADE = DataFacade.getInstance();

        System.out.println("\t - Starting Caches");
        initCaches();

        System.out.println("Start Complete\nPlease Enjoy - WAFL\n");
    }

    @Override
    public IDataFacade getDataFacade() {
        return DATA_FACADE;
    }

    @Override
    public IFileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    @Override
    public Factory getFactory() {
        return CREATOR;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getAppName() {
        return APP_NAME;
    }

    @Override
    public void setSelectedVault(IVault vault) {
        selectedVault = vault;
    }

    @Override
    public IVault getSelectedVault() {
        return selectedVault;
    }

    @Override
    public ICapsuleDomain getCapsuleDomain() {
        return CAPSULE_DOMAIN;
    }

    @Override
    public IStickerDomain getStickerDomain() {
        return STICKER_DOMAIN;
    }

    @Override
    public IVaultDomain getVaultDomain() {
        return VAULT_DOMAIN;
    }

    @Override
    public ISkinDomain getSkinDomain() {
        return SKIN_DOMAIN;
    }

    @Override
    public ISouvenirCaseDomain getSouvenirCaseDomain() {
        return SOUVENIR_CASE_DOMAIN;
    }

    @Override
    public List<Displayable> readAllItems(){
        List<Displayable> rtn = new ArrayList<>();
        rtn.addAll(CAPSULE_DOMAIN.readAllCapsules());
        rtn.addAll(SKIN_DOMAIN.readAllSkins());
        rtn.addAll(STICKER_DOMAIN.readAllStickers());
        rtn.addAll(SOUVENIR_CASE_DOMAIN.readAllSouvenirCases());
        return rtn;
    }

    private void initCaches(){
        System.out.println("\n\nThis might take some time...");
        CAPSULE_DOMAIN.readAllCapsules();
        SKIN_DOMAIN.readAllSkins();
        STICKER_DOMAIN.readAllStickers();
        SOUVENIR_CASE_DOMAIN.readAllSouvenirCases();
        VAULT_DOMAIN.readAllVaults();
        System.out.println("\t - Caching completed");
    }
}
