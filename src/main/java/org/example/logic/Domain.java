package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.*;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.IVault;
import org.example.logic.interfaces.sub.*;
import org.example.logic.sub.*;
import org.example.util.ConsoleColors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private static IDataFacade DATA_FACADE;
    private static Factory CREATOR;
    private static IFileHandler FILE_HANDLER;

    private static IVaultDomain VAULT_DOMAIN;
    private static ICapsuleDomain CAPSULE_DOMAIN;
    private static ISkinDomain SKIN_DOMAIN;
    private static ISouvenirCaseDomain SOUVENIR_CASE_DOMAIN;
    private static IStickerDomain STICKER_DOMAIN;
    private static IPatchDomain PATCH_DOMAIN;
    private static ICaseDomain CASE_DOMAIN;
    private static ITicketDomain TICKET_DOMAIN;
    private static IKeyDomain KEY_DOMAIN;
    private static IMusicKitDomain MUSIC_KIT_DOMAIN;
    private static IPinDomain PIN_DOMAIN;
    private static IPlayerModelDomain PLAYER_MODEL_DOMAIN;
    private static IGraffitiDomain GRAFFITI_DOMAIN;

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
        CREATOR = StructureCreator.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting FileHandler" + ConsoleColors.RESET);
        FILE_HANDLER = FileHandler.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting DataFacade" + ConsoleColors.RESET);
        DATA_FACADE = DataFacade.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting SubDomains" + ConsoleColors.RESET);
        VAULT_DOMAIN = VaultDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + VAULT_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        CAPSULE_DOMAIN = CapsuleDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + CAPSULE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        SKIN_DOMAIN = SkinDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + SKIN_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        SOUVENIR_CASE_DOMAIN = SouvenirCaseDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + SOUVENIR_CASE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        STICKER_DOMAIN = StickerDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + STICKER_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PATCH_DOMAIN = PatchDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PATCH_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        CASE_DOMAIN = CaseDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + CASE_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        TICKET_DOMAIN = TicketDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + TICKET_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        KEY_DOMAIN = KeyDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + KEY_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        MUSIC_KIT_DOMAIN = MusicKitDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + MUSIC_KIT_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PIN_DOMAIN = PinDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PIN_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        PLAYER_MODEL_DOMAIN = PlayerModelDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + PLAYER_MODEL_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);
        GRAFFITI_DOMAIN = GraffitiDomain.getInstance();
        System.out.println(ConsoleColors.BLUE + "\t\t - " + GRAFFITI_DOMAIN.getClass().getSimpleName() + ConsoleColors.RESET);

        



        System.out.println(ConsoleColors.YELLOW + "\t - Starting Caches" + ConsoleColors.RESET);
        initCaches();

        System.out.println(ConsoleColors.GREEN + "Start Complete" + ConsoleColors.RESET + "\n===========================================\n\n" + ConsoleColors.GREEN_BOLD + "Please Enjoy - WAFL" + ConsoleColors.RESET + "\n");
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
    public IPatchDomain getPatchDomain() {
        return PATCH_DOMAIN;
    }

    @Override
    public ICaseDomain getCaseDomain() {
        return CASE_DOMAIN;
    }

    @Override
    public ITicketDomain getTicketDomain() {
        return TICKET_DOMAIN;
    }

    @Override
    public IKeyDomain getKeyDomain(){
        return KEY_DOMAIN;
    }

    @Override
    public IMusicKitDomain getMusicKitDomain(){
        return MUSIC_KIT_DOMAIN;
    }

    @Override
    public IPinDomain getPinDomain() {
        return PIN_DOMAIN;
    }

    @Override
    public IPlayerModelDomain getPlayerModelDomain() {
        return PLAYER_MODEL_DOMAIN;
    }

    @Override
    public IGraffitiDomain getGraffitiDomain() {
        return GRAFFITI_DOMAIN;
    }

    @Override
    public List<Displayable> readAllItems(){
        List<Displayable> rtn = new ArrayList<>();
        rtn.addAll(CAPSULE_DOMAIN.readAllCapsules());
        rtn.addAll(SKIN_DOMAIN.readAllSkins());
        rtn.addAll(STICKER_DOMAIN.readAllStickers());
        rtn.addAll(SOUVENIR_CASE_DOMAIN.readAllSouvenirCases());
        rtn.addAll(PATCH_DOMAIN.readAllPatches());
        rtn.addAll(CASE_DOMAIN.readAllCases());
        rtn.addAll(TICKET_DOMAIN.readAllTickets());
        rtn.addAll(KEY_DOMAIN.readAllKeys());
        rtn.addAll(MUSIC_KIT_DOMAIN.readAllMusicKits());
        rtn.addAll(PIN_DOMAIN.readAllPins());
        rtn.addAll(PLAYER_MODEL_DOMAIN.readAllPlayerModels());
        rtn.addAll(GRAFFITI_DOMAIN.readAllGraffities());
        return rtn;
    }

    private void initCaches(){
        System.out.println("\n\nThis might take some time...");
        readAllItems();
        VAULT_DOMAIN.readAllVaults();
        System.out.println(ConsoleColors.GREEN + "\t - Caching completed" + ConsoleColors.RESET);
    }
}
