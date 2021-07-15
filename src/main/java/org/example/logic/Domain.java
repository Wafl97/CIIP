package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.interfaces.*;
import org.example.logic.interfaces.comps.Displayable;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Domain implements Logic {

    private static Domain instance;

    private final IDataFacade DATA_FACADE;
    private final Factory CREATOR;
    private final IFileHandler FILE_HANDLER;

    private List<ICapsule> capsulesCache;
    private List<IVault> vaultCache;
    private List<ISkin> skinCache;
    private List<ISouvenirCase> souvenirCaseCache;
    private List<ISticker> stickerCache;

    private IVault selectedVault;
    private ICapsule selectedCapsule;

    private static final String APP_NAME = "CIP";
    private static final String VERSION = "v0.5";

    public static Domain getInstance(){
        return instance == null ? instance = new Domain() : instance;
    }

    private Domain(){
        System.out.println("||======================================||");
        System.out.println("||\t\t\tStarting " + APP_NAME +" - "+ VERSION +"\t\t\t||");
        System.out.println("||======================================||");

        System.out.println("\t - Getting Factory");
        CREATOR = StructureCreator.getInstance();
        System.out.println("\t - Factory type: [" + CREATOR.getClass().getSimpleName() + "]");

        System.out.println("\t - Getting FileHandler");
        FILE_HANDLER = FileHandler.getInstance();

        System.out.println("\t - Getting DataFacade");
        DATA_FACADE = DataFacade.getInstance();

        System.out.println("Start Complete");
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
    public void setSelectedCapsule(ICapsule capsule) {
        selectedCapsule = capsule;
    }

    @Override
    public ICapsule getSelectedCapsule() {
        return selectedCapsule;
    }

    @Override
    public List<Displayable> readAllItems(){
        List<Displayable> rtn = new ArrayList<>();
        rtn.addAll(readAllCapsules());
        rtn.addAll(readAllSkins());
        rtn.addAll(readAllStickers());
        rtn.addAll(readAllSouvenirCases());
        return rtn;
    }

    @Override
    public List<ICapsule> readAllCapsules() {
        if (capsulesCache == null) {
            capsulesCache = new ArrayList<>();
            for (Object o : DATA_FACADE.getDataConnection().readAllCapsules()) {
                ICapsule newCapsule = CREATOR.emptyCapsule().convert2Obj((JSONObject) o);
                capsulesCache.add(newCapsule);
            }
        }
        return capsulesCache;
    }

    @Override
    public void createCapsule(ICapsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().createCapsule(capsule.convert2JSON());
        capsulesCache.add(capsule);
    }

    @Override
    public ICapsule readCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        return capsulesCache.stream().filter(capsule -> capsule.getId() == id).findFirst().get();
    }

    @Override
    public void updateCapsule(ICapsule capsule) {
        if (capsulesCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().updateCapsule(capsule.convert2JSON());
        capsulesCache.removeIf(cap -> cap.getId() == capsule.getId());
        capsulesCache.add(capsule);
    }

    @Override
    public void deleteCapsule(long id) {
        if (capsulesCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().deleteCapsule(id);
        capsulesCache.removeIf(capsule -> capsule.getId() == id);
    }

    @Override
    public List<ISkin> readAllSkins() {
        if (skinCache == null){
            skinCache = new ArrayList<>();
            for (Object o : DATA_FACADE.getDataConnection().readAllSkins()) {
                ISkin newSkin = CREATOR.emptySkin().convert2Obj((JSONObject) o);
                skinCache.add(newSkin);
            }
        }
        return skinCache;
    }

    @Override
    public void createSkin(ISkin skin) {
        if (skinCache == null) readAllCapsules();
        DATA_FACADE.getDataConnection().createSkin(skin.convert2JSON());
        skinCache.add(skin);
    }

    @Override
    public ISkin readSkin(long id) {
        if (skinCache == null) readAllSkins();
        return skinCache.stream().filter(skin -> skin.getId() == id).findFirst().get();
    }

    @Override
    public void updateSkin(ISkin skin) {
        if (skinCache == null) readAllSkins();
        DATA_FACADE.getDataConnection().updateSkin(skin.convert2JSON());
        skinCache.removeIf(skn -> skn.getId() == skin.getId());
        skinCache.add(skin);
    }

    @Override
    public void deleteSkin(long id) {
        if (skinCache == null) readAllSkins();
        DATA_FACADE.getDataConnection().deleteSKin(id);
        skinCache.removeIf(skin -> skin.getId() == id);
    }

    @Override
    public List<ISouvenirCase> readAllSouvenirCases() {
        if (souvenirCaseCache == null){
            souvenirCaseCache = new ArrayList<>();
            for (Object o : DATA_FACADE.getDataConnection().readAllSouvenirs()){
                ISouvenirCase newSouvenir = CREATOR.emptySouvenirCase().convert2Obj((JSONObject) o);
                souvenirCaseCache.add(newSouvenir);
            }
        }
        return souvenirCaseCache;
    }

    @Override
    public void createSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().createSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.add(souvenirCase);
    }

    @Override
    public ISouvenirCase readSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        return souvenirCaseCache.stream().filter(souvenir -> souvenir.getId() == id).findFirst().get();
    }

    @Override
    public void updateSouvenirCase(ISouvenirCase souvenirCase) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().updateSouvenir(souvenirCase.convert2JSON());
        souvenirCaseCache.removeIf(svn -> svn.getId() == souvenirCase.getId());
        souvenirCaseCache.add(souvenirCase);
    }

    @Override
    public void deleteSouvenirCase(long id) {
        if (souvenirCaseCache == null) readAllSouvenirCases();
        DATA_FACADE.getDataConnection().deleteSouvenir(id);
        souvenirCaseCache.removeIf(souvenirCase -> souvenirCase.getId() == id);
    }

    @Override
    public List<IVault> readAllVaults() {
        if (vaultCache == null) {
            vaultCache = new ArrayList<>();
            for (Object o : DATA_FACADE.getDataConnection().readAllVaults()) {
                IVault newVault = CREATOR.emptyVault().convert2Obj((JSONObject) o);
                vaultCache.add(newVault);
            }
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

    @Override
    public List<ISticker> readAllStickers() {
        if (stickerCache == null){
            stickerCache = new ArrayList<>();
            for (Object o : DATA_FACADE.getDataConnection().readAllStickers()){
                ISticker sticker = CREATOR.emptySticker().convert2Obj((JSONObject) o);
                stickerCache.add(sticker);
            }
        }
        return stickerCache;
    }

    @Override
    public void createSticker(ISticker sticker) {
        if (stickerCache == null) readAllStickers();
        stickerCache.add(sticker);
    }

    @Override
    public ISticker readSticker(long id) {
        if (stickerCache == null) readAllStickers();
        return stickerCache.stream().filter(sticker -> sticker.getId() == id).findFirst().get();
    }

    @Override
    public void updateSticker(ISticker sticker) {
        if (stickerCache == null) readAllStickers();
        DATA_FACADE.getDataConnection().updateSticker(sticker.convert2JSON());
        stickerCache.removeIf(stk -> stk.getId() == sticker.getId());
        stickerCache.add(sticker);
    }

    @Override
    public void deleteSticker(long id) {
        if (stickerCache == null) readAllStickers();
        stickerCache.removeIf(sticker -> sticker.getId() == id);
    }

}
