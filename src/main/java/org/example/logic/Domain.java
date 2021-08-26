package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.dto.interfaces.*;
import org.example.logic.interfaces.*;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.util.ConsoleColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.Attributes.*;

public final class Domain implements Logic {

    private static Domain instance;

    private static IDataFacade DATA_FACADE;
    private static IFactory CREATOR;
    private static IFileHandler FILE_HANDLER;
    private static IActionWriter WRITER;

    private static IGenericDomain<IVault>           VAULT_DOMAIN;
    private static IGenericDomain<ICapsule>         CAPSULE_DOMAIN;
    private static IGenericDomain<ISkin>            SKIN_DOMAIN;
    private static IGenericDomain<ISouvenirCase>    SOUVENIR_CASE_DOMAIN;
    private static IGenericDomain<ISticker>         STICKER_DOMAIN;
    private static IGenericDomain<IPatch>           PATCH_DOMAIN;
    private static IGenericDomain<ICase>            CASE_DOMAIN;
    private static IGenericDomain<ITicket>          TICKET_DOMAIN;
    private static IGenericDomain<IKey>             KEY_DOMAIN;
    private static IGenericDomain<IMusicKit>        MUSIC_KIT_DOMAIN;
    private static IGenericDomain<IPin>             PIN_DOMAIN;
    private static IGenericDomain<IPlayerModel>     PLAYER_MODEL_DOMAIN;
    private static IGenericDomain<IGraffiti>        GRAFFITI_DOMAIN;

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

    }

    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public void init(boolean print, boolean cacheItems, boolean loadGFX){

        if (print) {
            System.out.println("||==================================================||");
            System.out.println("||\t\t\t\t\t" + ConsoleColors.GREEN + "Starting " + getAppName() + ConsoleColors.RESET + "\t\t\t\t\t||");
            System.out.println("||==================================================||");
            System.out.println("Iteration: " + ConsoleColors.BLUE + getVersion() + ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE + "\nStarting Main Logic\n" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW + "\t - Getting Factory" + ConsoleColors.RESET);
            CREATOR = Factory.getInstance();
            System.out.println(ConsoleColors.YELLOW + "\t - Getting FileHandler" + ConsoleColors.RESET);
            FILE_HANDLER = FileHandler.getInstance();
            System.out.println(ConsoleColors.YELLOW + "\t - Getting DataFacade" + ConsoleColors.RESET);
            DATA_FACADE = DataFacade.getInstance();
            DATA_FACADE.init(true,loadGFX);
            System.out.println(ConsoleColors.YELLOW + "\t - Getting ActionWriter" + ConsoleColors.RESET);
            WRITER = ActionWriter.getInstance();
            System.out.println(ConsoleColors.YELLOW + "\t - Getting SubDomains" + ConsoleColors.RESET);
            VAULT_DOMAIN = new VaultDomain();
            System.out.println(ConsoleColors.BLUE + "\t\t - " + VAULT_DOMAIN.getType() + ConsoleColors.RESET);
            CAPSULE_DOMAIN = new GenericItemDomain(CAPSULE);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + CAPSULE_DOMAIN.getType() + ConsoleColors.RESET);
            SKIN_DOMAIN = new GenericItemDomain(SKIN);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + SKIN_DOMAIN.getType() + ConsoleColors.RESET);
            SOUVENIR_CASE_DOMAIN = new GenericItemDomain(SOUVENIR);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + SOUVENIR_CASE_DOMAIN.getType() + ConsoleColors.RESET);
            STICKER_DOMAIN = new GenericItemDomain(STICKER);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + STICKER_DOMAIN.getType() + ConsoleColors.RESET);
            PATCH_DOMAIN = new GenericItemDomain(PATCH);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + PATCH_DOMAIN.getType() + ConsoleColors.RESET);
            CASE_DOMAIN = new GenericItemDomain(CASE);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + CASE_DOMAIN.getType() + ConsoleColors.RESET);
            TICKET_DOMAIN = new GenericItemDomain(TICKET);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + TICKET_DOMAIN.getType() + ConsoleColors.RESET);
            KEY_DOMAIN = new GenericItemDomain(KEY);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + KEY_DOMAIN.getType() + ConsoleColors.RESET);
            MUSIC_KIT_DOMAIN = new GenericItemDomain(MUSICKIT);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + MUSIC_KIT_DOMAIN.getType() + ConsoleColors.RESET);
            PIN_DOMAIN = new GenericItemDomain(PIN);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + PIN_DOMAIN.getType() + ConsoleColors.RESET);
            PLAYER_MODEL_DOMAIN = new GenericItemDomain(PLAYERMODEL);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + PLAYER_MODEL_DOMAIN.getType() + ConsoleColors.RESET);
            GRAFFITI_DOMAIN = new GenericItemDomain(GRAFFITI);
            System.out.println(ConsoleColors.BLUE + "\t\t - " + GRAFFITI_DOMAIN.getType() + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW + "\t - Starting Caches" + ConsoleColors.RESET);
            if (cacheItems) initCaches();
            System.out.println(ConsoleColors.GREEN + "Start Complete" + ConsoleColors.RESET + "\n===========================================\n\n" + ConsoleColors.GREEN_BOLD + "Please Enjoy - WAFL" + ConsoleColors.RESET + "\n");
        }
        else {
            CREATOR = Factory.getInstance();
            FILE_HANDLER = FileHandler.getInstance();
            DATA_FACADE = DataFacade.getInstance();
            DATA_FACADE.init(false,loadGFX);
            WRITER = ActionWriter.getInstance();
            VAULT_DOMAIN = new VaultDomain();
            CAPSULE_DOMAIN = new GenericItemDomain(CAPSULE);
            SKIN_DOMAIN = new GenericItemDomain(SKIN);
            SOUVENIR_CASE_DOMAIN = new GenericItemDomain(SOUVENIR);
            STICKER_DOMAIN = new GenericItemDomain(STICKER);
            PATCH_DOMAIN = new GenericItemDomain(PATCH);
            CASE_DOMAIN = new GenericItemDomain(CASE);
            TICKET_DOMAIN = new GenericItemDomain(TICKET);
            KEY_DOMAIN = new GenericItemDomain(KEY);
            MUSIC_KIT_DOMAIN = new GenericItemDomain(MUSICKIT);
            PIN_DOMAIN = new GenericItemDomain(PIN);
            PLAYER_MODEL_DOMAIN = new GenericItemDomain(PLAYERMODEL);
            GRAFFITI_DOMAIN = new GenericItemDomain(GRAFFITI);
            if (cacheItems) initCaches();
        }
        DATA_FACADE.getLogWriter().writeLog("== SYS == STARTED CIIP ==");
        if (cacheItems) DATA_FACADE.getLogWriter().writeLog("== SYS == TOTAL CACHE " + (readAllItems().size() + VAULT_DOMAIN.readAll().size()) + " ==");
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
    public IFactory getFactory() {
        return CREATOR;
    }

    @Override
    public IActionWriter getActionWriter() {
        return WRITER;
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
    public IGenericDomain<ICapsule> getCapsuleDomain() {
        return CAPSULE_DOMAIN;
    }

    @Override
    public IGenericDomain<ISticker> getStickerDomain() {
        return STICKER_DOMAIN;
    }

    @Override
    public IGenericDomain<ISkin> getSkinDomain() {
        return SKIN_DOMAIN;
    }

    @Override
    public IGenericDomain<IVault> getVaultDomain() {
        return VAULT_DOMAIN;
    }

    @Override
    public IGenericDomain<ISouvenirCase> getSouvenirCaseDomain() {
        return SOUVENIR_CASE_DOMAIN;
    }

    @Override
    public IGenericDomain<IPatch> getPatchDomain() {
        return PATCH_DOMAIN;
    }

    @Override
    public IGenericDomain<ICase> getCaseDomain() {
        return CASE_DOMAIN;
    }

    @Override
    public IGenericDomain<ITicket> getTicketDomain() {
        return TICKET_DOMAIN;
    }

    @Override
    public IGenericDomain<IKey> getKeyDomain(){
        return KEY_DOMAIN;
    }

    @Override
    public IGenericDomain<IMusicKit> getMusicKitDomain(){
        return MUSIC_KIT_DOMAIN;
    }

    @Override
    public IGenericDomain<IPin> getPinDomain() {
        return PIN_DOMAIN;
    }

    @Override
    public IGenericDomain<IPlayerModel> getPlayerModelDomain() {
        return PLAYER_MODEL_DOMAIN;
    }

    @Override
    public IGenericDomain<IGraffiti> getGraffitiDomain() {
        return GRAFFITI_DOMAIN;
    }

    @Override
    public List<Identifiable> readAllItems(){
        List<Identifiable> rtn = new ArrayList<>();
        rtn.addAll(CAPSULE_DOMAIN.readAll());
        rtn.addAll(SKIN_DOMAIN.readAll());
        rtn.addAll(STICKER_DOMAIN.readAll());
        rtn.addAll(SOUVENIR_CASE_DOMAIN.readAll());
        rtn.addAll(PATCH_DOMAIN.readAll());
        rtn.addAll(CASE_DOMAIN.readAll());
        rtn.addAll(TICKET_DOMAIN.readAll());
        rtn.addAll(KEY_DOMAIN.readAll());
        rtn.addAll(MUSIC_KIT_DOMAIN.readAll());
        rtn.addAll(PIN_DOMAIN.readAll());
        rtn.addAll(PLAYER_MODEL_DOMAIN.readAll());
        rtn.addAll(GRAFFITI_DOMAIN.readAll());
        return rtn;
    }

    private void initCaches(){
        System.out.println("\n\nThis might take some time...");
        readAllItems();
        VAULT_DOMAIN.readAll();
        System.out.println(ConsoleColors.GREEN + "\t - Caching completed" + ConsoleColors.RESET);
    }
}
