package org.example.data;

import org.example.data.interfaces.DataConnection;

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
    public boolean connect(){
        if (!connected) {
            System.out.println("Initiating connecting");
            try {
                // Get the path for the database
                URL url = getClass().getClassLoader().getResource(PATH);
                // Check if the path is valid
                if (url == null) throw new FileNotFoundException("Directory: " + PATH + " does not exist.");
                // Creates the a file object to the directory
                File directory = new File(url.toURI());

                if (!directory.isDirectory()) throw new NotDirectoryException(PATH + " is not a directory.");

                // Get all files from directory
                File[] files = directory.listFiles();

                // Check if is directory
                if (files == null) throw new NotDirectoryException(PATH + " is not a directory.");

                // Populate tables hashmap
                System.out.println("Loading files");
                for (File file : files) {
                    // Get name of file
                    String fileName = file.getName().split("\\.")[0];
                    System.out.println(file);
                    fileMap.put(fileName, file);
                }
                connected = true;
                return true;
            } catch (URISyntaxException | FileNotFoundException | NotDirectoryException e) {
                connected = false;
                return false;
            }
        }
        System.out.println("Already connected");
        return true;
    }

    private void saveFile(JSONArray jsonArray, String file){
        File jsonFile = fileMap.get(file);
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONArray loadFile(String file){
        try(FileReader reader = new FileReader(fileMap.get(file).getPath())) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            return (JSONArray) obj;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject readOneObj(long id, List<JSONObject> objs, String objName){
        for (Object obj : objs){
            JSONObject shellObj = (JSONObject) obj;
            JSONObject innerObj = (JSONObject) shellObj.get(objName);
            if ((long) innerObj.get(ID.toString()) == id){
                return shellObj;
            }
        }
        return null;
    }

    private void addObjToTable(JSONObject obj, String table){
        JSONArray array = loadFile(table);
        array.add(obj);
        saveFile(array,table);
    }

    private void removeObjFromTable(long id, String table, String objName) {
        JSONArray jsonArray = loadFile(table);
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject outerObj = (JSONObject) o;
                JSONObject innerObj = (JSONObject) outerObj.get(objName);
                if ((long) innerObj.get(ID.toString()) == id) {
                    jsonArray.remove(o);
                    break;
                }
            }
            saveFile(jsonArray, table);
        }
    }

    private void updateObjInTable(JSONObject jsonObject, String table, String objName) {
        long id = (long) ((JSONObject) jsonObject.get(objName)).get(ID.toString());
        JSONArray jsonArray = loadFile(table);
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject jObject = (JSONObject) o;
                JSONObject internalObject = (JSONObject) jObject.get(objName);
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
        return new ArrayList<JSONObject>(loadFile(VAULTS.toString()));
    }

    @Override
    public void createVault(JSONObject jsonObject) {
        addObjToTable(jsonObject,VAULTS.toString());
    }

    @Override
    public JSONObject readVault(long id) {
        return readOneObj(id, readAllVaults(), VAULT.toString());
    }

    @Override
    public void updateVault(JSONObject jsonObject) {
        updateObjInTable(jsonObject, VAULTS.toString(), VAULT.toString());
    }

    @Override
    public void deleteVault(long id) {
        removeObjFromTable(id, VAULTS.toString(), VAULT.toString());
    }

    @Override
    public List<JSONObject> readAllCapsules() {
        return new ArrayList<JSONObject>(loadFile(CAPSULES.toString()));
    }

    @Override
    public void createCapsule(JSONObject jsonObject) {
        addObjToTable(jsonObject,CAPSULES.toString());
    }

    @Override
    public JSONObject readCapsule(long id) {
        return readOneObj(id, readAllCapsules(), CAPSULE.toString());
    }

    @Override
    public void updateCapsule(JSONObject jsonObject) {
        updateObjInTable(jsonObject,CAPSULES.toString(),CAPSULE.toString());
    }

    // FIXME: 05-07-2021 SIMPLIFY
    @Override
    public void deleteCapsule(long id) {
        //Remove from investments
        JSONArray investArray = loadFile(VAULTS.toString());
        if (investArray != null) {
            for (Object o : investArray) {
                JSONObject investObj = (JSONObject) o;
                JSONObject innerObj = (JSONObject) investObj.get(VAULT.toString());
                JSONArray innerInvestArray = (JSONArray) innerObj.get(CAPSULES.toString());
                for (Object obj : innerInvestArray) {
                    JSONObject investCapsule = (JSONObject) obj;
                    JSONObject finalObj = (JSONObject) investCapsule.get(CAPSULE.toString());
                    if ((long) finalObj.get(CAPSULE_ID.toString()) == id) {
                        innerInvestArray.remove(obj);
                        break;
                    }
                }
            }
            saveFile(investArray, VAULTS.toString());
        }

        //Remove from capsules list
        removeObjFromTable(id, CAPSULES.toString(), CAPSULE.toString());
    }

    // FIXME: 05-07-2021
    @Override
    public List<JSONObject> readAllSouvenirs() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 05-07-2021
    @Override
    public void createSouvenir(JSONObject jsonObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 05-07-2021
    @Override
    public JSONObject readSouvenir(long id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 05-07-2021
    @Override
    public void updateSouvenir(JSONObject jsonObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FIXME: 05-07-2021
    @Override
    public void deleteSouvenir(long id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<JSONObject> readAllSkins() {
        return new ArrayList<JSONObject>(loadFile(SKINS.toString()));
    }

    @Override
    public void createSkin(JSONObject jsonObject) {
        addObjToTable(jsonObject,SKINS.toString());
    }

    @Override
    public JSONObject readSkin(long id) {
        return  readOneObj(id,readAllSkins(),SKIN.toString());
    }

    @Override
    public void updateSkin(JSONObject jsonObject) {
        updateObjInTable(jsonObject,SKINS.toString(),SKIN.toString());
    }

    @Override
    public void deleteSKin(long id) {
        removeObjFromTable(id,SKINS.toString(),SKIN.toString());
    }
}
