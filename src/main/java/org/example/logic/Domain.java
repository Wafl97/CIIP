package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.dto.interfaces.IVault;
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

    private static IGenericDomain VAULT_DOMAIN;
    private static IGenericDomain CAPSULE_DOMAIN;
    private static IGenericDomain SKIN_DOMAIN;
    private static IGenericDomain SOUVENIR_CASE_DOMAIN;
    private static IGenericDomain STICKER_DOMAIN;
    private static IGenericDomain PATCH_DOMAIN;
    private static IGenericDomain CASE_DOMAIN;
    private static IGenericDomain TICKET_DOMAIN;
    private static IGenericDomain KEY_DOMAIN;
    private static IGenericDomain MUSIC_KIT_DOMAIN;
    private static IGenericDomain PIN_DOMAIN;
    private static IGenericDomain PLAYER_MODEL_DOMAIN;
    private static IGenericDomain GRAFFITI_DOMAIN;

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

    public void init(){

        System.out.println("||==================================================||");
        System.out.println("||\t\t\t\t\t" + ConsoleColors.GREEN + "Starting " + getAppName() + ConsoleColors.RESET + "\t\t\t\t\t||");
        System.out.println("||==================================================||");
        System.out.println("Iteration: "  + ConsoleColors.BLUE + getVersion() + ConsoleColors.RESET);

        System.out.println(ConsoleColors.PURPLE + "\nStarting Main Logic\n" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW + "\t - Getting Factory" + ConsoleColors.RESET);
        CREATOR = Factory.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting FileHandler" + ConsoleColors.RESET);
        FILE_HANDLER = FileHandler.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting ActionWriter" + ConsoleColors.RESET);
        WRITER = ActionWriter.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting DataFacade" + ConsoleColors.RESET);
        DATA_FACADE = DataFacade.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting SubDomains" + ConsoleColors.RESET);
        VAULT_DOMAIN = new GenericDomain(VAULT);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + VAULT_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        CAPSULE_DOMAIN = new GenericDomain(CAPSULE);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + CAPSULE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        SKIN_DOMAIN = new GenericDomain(SKIN);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + SKIN_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        SOUVENIR_CASE_DOMAIN = new GenericDomain(SOUVENIR);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + SOUVENIR_CASE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        STICKER_DOMAIN = new GenericDomain(STICKER);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + STICKER_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PATCH_DOMAIN = new GenericDomain(PATCH);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PATCH_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        CASE_DOMAIN = new GenericDomain(CASE);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + CASE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        TICKET_DOMAIN = new GenericDomain(TICKET);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + TICKET_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        KEY_DOMAIN = new GenericDomain(KEY);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + KEY_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        MUSIC_KIT_DOMAIN = new GenericDomain(MUSICKIT);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + MUSIC_KIT_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PIN_DOMAIN = new GenericDomain(PIN);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PIN_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PLAYER_MODEL_DOMAIN = new GenericDomain(PLAYERMODEL);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PLAYER_MODEL_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        GRAFFITI_DOMAIN = new GenericDomain(GRAFFITI);
        System.out.println(ConsoleColors.BLUE + "\t\t - " + GRAFFITI_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);

        



        System.out.println(ConsoleColors.YELLOW + "\t - Starting Caches" + ConsoleColors.RESET);
        initCaches();

        System.out.println(ConsoleColors.GREEN + "Start Complete" + ConsoleColors.RESET + "\n===========================================\n\n" + ConsoleColors.GREEN_BOLD + "Please Enjoy - WAFL" + ConsoleColors.RESET + "\n");
        DataFacade.getInstance().getLogWriter().writeLog("== SYS == STARTED CIIP ==");
        DataFacade.getInstance().getLogWriter().writeLog("== SYS == TOTAL CACHE " + (readAllItems().size() + VAULT_DOMAIN.readAll().size()) + " ==");
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
    public IGenericDomain getCapsuleDomain() {
        return CAPSULE_DOMAIN;
    }

    @Override
    public IGenericDomain getStickerDomain() {
        return STICKER_DOMAIN;
    }

    @Override
    public IGenericDomain getSkinDomain() {
        return SKIN_DOMAIN;
    }

    @Override
    public IGenericDomain getVaultDomain() {
        return VAULT_DOMAIN;
    }

    @Override
    public IGenericDomain getSouvenirCaseDomain() {
        return SOUVENIR_CASE_DOMAIN;
    }

    @Override
    public IGenericDomain getPatchDomain() {
        return PATCH_DOMAIN;
    }

    @Override
    public IGenericDomain getCaseDomain() {
        return CASE_DOMAIN;
    }

    @Override
    public IGenericDomain getTicketDomain() {
        return TICKET_DOMAIN;
    }

    @Override
    public IGenericDomain getKeyDomain(){
        return KEY_DOMAIN;
    }

    @Override
    public IGenericDomain getMusicKitDomain(){
        return MUSIC_KIT_DOMAIN;
    }

    @Override
    public IGenericDomain getPinDomain() {
        return PIN_DOMAIN;
    }

    @Override
    public IGenericDomain getPlayerModelDomain() {
        return PLAYER_MODEL_DOMAIN;
    }

    @Override
    public IGenericDomain getGraffitiDomain() {
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
