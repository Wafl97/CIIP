package org.example.data;

import org.example.data.interfaces.DataConnection;

import org.example.util.Attributes;
import org.example.util.ConsoleColors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.*;

import static org.example.util.Attributes.*;

final class JsonConnection implements DataConnection {

    //Data path
    private static final String PATH = "org/example/datacollection/";

    private static JsonConnection instance;
    private final Map<String,File> fileMap = new HashMap<>();
    private boolean connected = false;

    private JsonConnection(){
    }

    public static DataConnection getInstance() {
        return instance == null ? instance = new JsonConnection() : instance;
    }

    @Override
    public boolean connect(boolean print){
        if (!connected) {
            if (print) {
                System.out.println("\n||===========================================");
                System.out.println("|| " + ConsoleColors.PURPLE + "Initiating connection" + ConsoleColors.RESET);
            }
            try {
                // Get the path for the database
                URL url = getClass().getClassLoader().getResource(PATH);
                // Check if the path is valid
                if (url == null) throw new FileNotFoundException("Directory: " + PATH + " does not exist.");
                // Creates the a file object to the directory
                File directory = new File(url.toURI());

                if (!directory.isDirectory()) throw new NotDirectoryException(PATH + " is not a directory.");
                if (print) System.out.println("|| Loading files from [" + ConsoleColors.BLUE + directory + ConsoleColors.RESET + "]");

                // Get all files from directory
                File[] files = directory.listFiles();

                // Check if is directory
                if (files == null) throw new NotDirectoryException(PATH + " is not a directory.");
                if (print) System.out.println("|| Total files found [" + ConsoleColors.CYAN + files.length + ConsoleColors.RESET + "]");

                // Populate tables hashmap
                long totalBytes = 0;
                for (File file : files) {
                    // Get name of file
                    String fileName = file.getName().split("\\.")[0];
                if (print) {
                    System.out.println("|| " + ConsoleColors.YELLOW + "Found file " + ConsoleColors.RESET + "[" + ConsoleColors.BLUE + fileName + ConsoleColors.RESET + "] : Size [" + ConsoleColors.CYAN + file.length() + ConsoleColors.RESET + "] b");
                    totalBytes += file.length();
                }
                    fileMap.put(fileName, file);
                }
                if (print) {
                    System.out.println("|| Total Size[" + ConsoleColors.CYAN + totalBytes + ConsoleColors.RESET + "] b");
                    System.out.println("|| " + ConsoleColors.GREEN + "Initialisation done" + ConsoleColors.RESET);
                    System.out.println("||===========================================\n");
                }
                connected = true;
                return true;
            } catch (URISyntaxException | FileNotFoundException | NotDirectoryException e) {
                connected = false;
                return false;
            }
        }
        System.out.println(ConsoleColors.GREEN + "Connected"  + ConsoleColors.RESET);
        return true;
    }

    private void saveFile(JSONArray jsonArray, Attributes file){
        File jsonFile = fileMap.get(file.toString());
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray loadFile(Attributes file){
        try(FileReader reader = new FileReader(fileMap.get(file.toString()).getPath())) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject readOneObj(long id, List<JSONObject> objs, Attributes objName){
        for (JSONObject obj : objs){
            JSONObject innerObj = (JSONObject) obj.get(objName.toString());
            if ((long) innerObj.get(ID.toString()) == id){
                return obj;
            }
        }
        return null;
    }

    private void addObjToTable(JSONObject obj, Attributes table){
        JSONArray array = loadFile(table);
        array.add(obj);
        saveFile(array,table);
    }

    private void removeObjFromTable(long id, Attributes table, Attributes objName, boolean cascade) {
        JSONArray jsonArray = loadFile(table);
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject outerObj = (JSONObject) o;
                JSONObject innerObj = (JSONObject) outerObj.get(objName.toString());
                if ((long) innerObj.get(ID.toString()) == id) {
                    jsonArray.remove(o);
                    break;
                }
            }
            saveFile(jsonArray, table);
        }
        if (cascade){
            //Remove from investments
            JSONArray investArray = loadFile(VAULTS);
            if (investArray != null) {
                for (Object o : investArray) {
                    JSONObject investObj = (JSONObject) o;
                    JSONObject innerObj = (JSONObject) investObj.get(VAULT.toString());
                    JSONArray innerInvestArray = (JSONArray) innerObj.get(table.toString());
                    for (Object obj : innerInvestArray) {
                        JSONObject investCapsule = (JSONObject) obj;
                        JSONObject finalObj = (JSONObject) investCapsule.get(objName.toString());
                        if ((long) finalObj.get(ID.toString()) == id) {
                            innerInvestArray.remove(obj);
                            break;
                        }
                    }
                }
                saveFile(investArray, VAULTS);
            }
        }
    }

    private void updateObjInTable(JSONObject jsonObject, Attributes table, Attributes objName) {
        long id = (long) ((JSONObject) jsonObject.get(objName.toString())).get(ID.toString());
        JSONArray jsonArray = loadFile(table);
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject jObject = (JSONObject) o;
                JSONObject internalObject = (JSONObject) jObject.get(objName.toString());
                if ((long) internalObject.get(ID.toString()) == id) {
                    jsonArray.remove(o);
                    jsonArray.add(jsonObject);
                    break;
                }
            }
            saveFile(jsonArray, table);
        }
    }

    @Override
    public List<JSONObject> readAllVaults() {
        return new ArrayList<JSONObject>(loadFile(VAULTS));
    }

    @Override
    public void createVault(JSONObject jsonObject) {
        addObjToTable(jsonObject,VAULTS);
    }

    @Override
    public JSONObject readVault(long id) {
        return readOneObj(id, readAllVaults(), VAULT);
    }

    @Override
    public void updateVault(JSONObject jsonObject) {
        updateObjInTable(jsonObject, VAULTS, VAULT);
    }

    @Override
    public void deleteVault(long id) {
        removeObjFromTable(id, VAULTS, VAULT, false);
    }

    @Override
    public List<JSONObject> readAllCapsules() {
        return new ArrayList<JSONObject>(loadFile(CAPSULES));
    }

    @Override
    public void createCapsule(JSONObject jsonObject) {
        addObjToTable(jsonObject,CAPSULES);
    }

    @Override
    public JSONObject readCapsule(long id) {
        return readOneObj(id, readAllCapsules(), CAPSULE);
    }

    @Override
    public void updateCapsule(JSONObject jsonObject) {
        updateObjInTable(jsonObject,CAPSULES,CAPSULE);
    }

    @Override
    public void deleteCapsule(long id) {
        removeObjFromTable(id, CAPSULES, CAPSULE, true);
    }

    @Override
    public List<JSONObject> readAllSouvenirs() {
        return new ArrayList<JSONObject>(loadFile(SOUVENIRS));
    }

    @Override
    public void createSouvenir(JSONObject jsonObject) {
        addObjToTable(jsonObject,SOUVENIRS);
    }

    @Override
    public JSONObject readSouvenir(long id) {
        return readOneObj(id,readAllSouvenirs(),SOUVENIR);
    }

    @Override
    public void updateSouvenir(JSONObject jsonObject) {
        updateObjInTable(jsonObject,SOUVENIRS,SOUVENIR);
    }

    @Override
    public void deleteSouvenir(long id) {
        removeObjFromTable(id,SOUVENIRS,SOUVENIR,true);
    }

    @Override
    public List<JSONObject> readAllSkins() {
        return new ArrayList<JSONObject>(loadFile(SKINS));
    }

    @Override
    public void createSkin(JSONObject jsonObject) {
        addObjToTable(jsonObject,SKINS);
    }

    @Override
    public JSONObject readSkin(long id) {
        return  readOneObj(id,readAllSkins(),SKIN);
    }

    @Override
    public void updateSkin(JSONObject jsonObject) {
        updateObjInTable(jsonObject,SKINS,SKIN);
    }

    @Override
    public void deleteSKin(long id) {
        removeObjFromTable(id,SKINS,SKIN,true);
    }

    @Override
    public List<JSONObject> readAllStickers() {
        return new ArrayList<JSONObject>(loadFile(STICKERS));
    }

    @Override
    public void createSticker(JSONObject jsonObject) {
        addObjToTable(jsonObject,STICKERS);
    }

    @Override
    public JSONObject readSticker(long id) {
        return readOneObj(id,readAllStickers(),STICKER);
    }

    @Override
    public void updateSticker(JSONObject jsonObject) {
        updateObjInTable(jsonObject,STICKERS,STICKER);
    }

    @Override
    public void deleteSticker(long id) {
        removeObjFromTable(id,STICKERS,STICKER,true);
    }

    @Override
    public List<JSONObject> readAllPatches() {
        return new ArrayList<JSONObject>(loadFile(PATCHES));
    }

    @Override
    public void createPatch(JSONObject jsonObject) {
        addObjToTable(jsonObject,PATCHES);
    }

    @Override
    public JSONObject readPatch(long id) {
        return readOneObj(id,readAllPatches(),PATCH);
    }

    @Override
    public void updatePatch(JSONObject jsonObject) {
        updateObjInTable(jsonObject,PATCHES,PATCH);
    }

    @Override
    public void deletePatch(long id) {
        removeObjFromTable(id,PATCHES,PATCH,true);
    }

    @Override
    public List<JSONObject> readAllCases() {
        return new ArrayList<JSONObject>(loadFile(CASES));
    }

    @Override
    public void createCase(JSONObject jsonObject) {
        addObjToTable(jsonObject,CASES);
    }

    @Override
    public JSONObject readCase(long id) {
        return readOneObj(id,readAllCases(),CASE);
    }

    @Override
    public void updateCase(JSONObject jsonObject) {
        updateObjInTable(jsonObject,CASES,CASE);
    }

    @Override
    public void deleteCase(long id) {
        removeObjFromTable(id,CASES,CASE,true);
    }

    @Override
    public List<JSONObject> readAllTickets() {
        return new ArrayList<JSONObject>(loadFile(TICKETS));
    }

    @Override
    public void createTicket(JSONObject jsonObject) {
        addObjToTable(jsonObject,TICKETS);
    }

    @Override
    public JSONObject readTicket(long id) {
        return readOneObj(id,readAllTickets(),TICKET);
    }

    @Override
    public void updateTicket(JSONObject jsonObject) {
        updateObjInTable(jsonObject,TICKETS,TICKET);
    }

    @Override
    public void deleteTicket(long id) {
        removeObjFromTable(id,TICKETS,TICKET,true);
    }

    @Override
    public List<JSONObject> readAllKeys() {
        return new ArrayList<JSONObject>(loadFile(KEYS));
    }

    @Override
    public void createKey(JSONObject jsonObject) {
        addObjToTable(jsonObject,KEYS);
    }

    @Override
    public JSONObject readKey(long id) {
        return readOneObj(id,readAllKeys(),KEY);
    }

    @Override
    public void updateKey(JSONObject jsonObject) {
        updateObjInTable(jsonObject,KEYS,KEY);
    }

    @Override
    public void deleteKey(long id) {
        removeObjFromTable(id,KEYS,KEY,true);
    }

    @Override
    public List<JSONObject> readAllMusicKits() {
        return new ArrayList<JSONObject>(loadFile(MUSICKITS));
    }

    @Override
    public void createMusicKit(JSONObject jsonObject) {
        addObjToTable(jsonObject,MUSICKITS);
    }

    @Override
    public JSONObject readMusicKit(long id) {
        return readOneObj(id,readAllMusicKits(),MUSICKIT);
    }

    @Override
    public void updateMusicKit(JSONObject jsonObject) {
        updateObjInTable(jsonObject,MUSICKITS,MUSICKIT);
    }

    @Override
    public void deleteMusicKit(long id) {
        removeObjFromTable(id,MUSICKITS,MUSICKIT,true);
    }

    @Override
    public List<JSONObject> readAllPins() {
        return new ArrayList<JSONObject>(loadFile(PINS));
    }

    @Override
    public void createPin(JSONObject jsonObject) {
        addObjToTable(jsonObject,PINS);
    }

    @Override
    public JSONObject readPin(long id) {
        return readOneObj(id,readAllPins(),PIN);
    }

    @Override
    public void updatePin(JSONObject jsonObject) {
        updateObjInTable(jsonObject,PINS,PIN);
    }

    @Override
    public void deletePin(long id) {
        removeObjFromTable(id,PINS,PIN,true);
    }
}
