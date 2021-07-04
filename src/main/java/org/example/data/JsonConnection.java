package org.example.data;

import org.example.data.Interfaces.DataConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.*;

import static org.example.Util.Attributes.*;

public class JsonConnection implements DataConnection {

    //Data path
    private static final String PATH = "org/example/datacollection/";

    private static JsonConnection instance;
    private final Map<String,File> fileMap = new HashMap<>();

    private JsonConnection(){
        System.out.println("JSON connection: " + connect());
    }

    public static DataConnection getInstance() {
        return instance == null ? instance = new JsonConnection() : instance;
    }


    private boolean connect(){
        try {
            // Get the path for the database
            URL url = getClass().getClassLoader().getResource(PATH);
            // Check if the path is valid
            if(url == null) throw new FileNotFoundException("Directory: " + PATH + " does not exist.");
            // Creates the a file object to the directory
            File directory = new File(url.toURI());

            if(!directory.isDirectory()) throw new NotDirectoryException(PATH + " is not a directory.");

            // Get all files from directory
            File[] files = directory.listFiles();

            // Check if is directory
            if(files == null) throw new NotDirectoryException(PATH + " is not a directory.");

            // Populate tables hashmap
            for(File file : files) {
                // Get name of file
                String fileName = file.getName().split("\\.")[0];

                fileMap.put(fileName, file);
            }
            return true;
        } catch (URISyntaxException | FileNotFoundException | NotDirectoryException e) {
            return false;
        }
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

    private void updateObjInTable(long id, JSONObject jsonObject, String capsule, String capsules) {
        JSONArray jsonArray = loadFile(CAPSULES.toString());
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                JSONObject jObject = (JSONObject) o;
                JSONObject internalObject = (JSONObject) jObject.get(capsule);
                if ((long) internalObject.get(ID) == id) {
                    jsonArray.remove(o);
                    jsonArray.add(jsonObject);
                    break;
                }
            }
            saveFile(jsonArray, capsules);
        }
    }

    @Override
    public List<JSONObject> readAllInvestments() {
        return new ArrayList<JSONObject>(loadFile(INVESTMENTS.toString()));
    }

    @Override
    public void createInvestment(JSONObject jsonObject) {
        JSONArray jsonArray = loadFile(INVESTMENTS.toString());
        jsonArray.add(jsonObject);
        saveFile(jsonArray,INVESTMENTS.toString());
    }

    @Override
    public JSONObject readInvestment(long id) {
        for (Object obj : readAllInvestments()){
            JSONObject shellObj = (JSONObject) obj;
            JSONObject innerObj = (JSONObject) shellObj.get(VAULT);
            if ((long) innerObj.get(ID) == id){
                return shellObj;
            }
        }
        return null;
    }

    @Override
    public void updateInvestment(JSONObject jsonObject) {
        long id = (long) ((JSONObject) jsonObject.get(VAULT)).get(ID);
        updateObjInTable(id, jsonObject, VAULT.toString(), INVESTMENTS.toString());
    }

    @Override
    public void deleteInvestment(long id) {
        removeObjFromTable(id, INVESTMENTS.toString(), VAULT.toString());
    }

    @Override
    public List<JSONObject> readAllCapsules() {
        return new ArrayList<JSONObject>(loadFile(CAPSULES.toString()));
    }

    @Override
    public void createCapsule(JSONObject jsonObject) {
        JSONArray jsonArray = loadFile(CAPSULES.toString());
        jsonArray.add(jsonObject);
        saveFile(jsonArray, CAPSULES.toString());
    }

    @Override
    public JSONObject readCapsule(long id) {
        for (Object obj : readAllCapsules()){
            JSONObject shellObj = (JSONObject) obj;
            JSONObject innerObj = (JSONObject) shellObj.get(CAPSULE.toString());
            if ((long) innerObj.get(ID.toString()) == id){
                return shellObj;
            }
        }
        return null;
    }

    @Override
    public void updateCapsule(JSONObject jsonObject) {
        long id = (long) ((JSONObject) jsonObject.get(CAPSULE)).get(ID);
        updateObjInTable(id, jsonObject, CAPSULE.toString(), CAPSULES.toString());
    }

    @Override
    public void deleteCapsule(long id) {
        //Remove from investments
        JSONArray investArray = loadFile(INVESTMENTS.toString());
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
            saveFile(investArray, INVESTMENTS.toString());
        }

        //Remove from capsules list
        removeObjFromTable(id, CAPSULES.toString(), CAPSULE.toString());
    }
}
