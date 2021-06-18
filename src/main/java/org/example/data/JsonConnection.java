package org.example.data;

import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Factory;
import org.example.logic.Interfaces.Investment;
import org.example.logic.StructureCreator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.*;

public class JsonConnection implements DataConnection {

    private static final String PATH = "org/example/datacollection/";
    private static final String INVESTMENTS = "investments";
    private static final String VAULT = "vault";
    private static final String CAPSULES = "capsules";
    private static final String CAPSULE = "capsule";

    private static JsonConnection instance;
    private List<Investment> investments;
    private List<IItem> containers;
    private final Factory factory = StructureCreator.getInstance();
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

    private long findMaxInvestmentId(){
        long maxValue = investments.get(0).getId();
        for (Investment investment : investments) {
            if (investment.getId() > maxValue) maxValue = investment.getId();
        }
        return maxValue;
    }

    private long findMaxContainerId(){
        long maxValue = containers.get(0).getId();
        for (IItem container : containers) {
            if (container.getId() > maxValue) maxValue = container.getId();
        }
        return maxValue;
    }

    @Override
    public List<Investment> readAllInvestments() {
        if (investments == null){
            investments = new ArrayList<>();
            JSONArray jsonArray = loadFile(INVESTMENTS);
            if (jsonArray != null) {
                for (Object o : jsonArray) {
                    Investment investment = factory.emptyVault();
                    JSONObject jsonObject = (JSONObject) o;
                    JSONObject vault = (JSONObject) jsonObject.get(VAULT);
                    investment.populate(
                            (long) vault.get("id"),
                            (String) vault.get("name")
                    );
                    JSONArray capArray = (JSONArray) vault.get(CAPSULES);
                    for (Object object : capArray) {
                        JSONObject jsonCaps = (JSONObject) object;
                        JSONObject cap = (JSONObject) jsonCaps.get(CAPSULE);
                        investment.addItems(readCapsule((long) cap.get("capsule_id")), (long) cap.get("amount"));
                    }
                    investments.add(investment);
                }
            }
        }
        return investments;
    }

    @Override
    public void createInvestment(Investment investment) {
        if (investments == null) readAllCapsules();
        investments.add(investment);
        JSONObject investObj = new JSONObject();
        long maxId = findMaxInvestmentId() + 1;
        investObj.put("id",maxId);
        investObj.put("name",investment.getName());
        JSONArray containerArray = new JSONArray();
        for (IItem container : investment.getItems()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("capsule_id",container.getId());
            jsonObject.put("amount",investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put("capsule",jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put("capsules",containerArray);
        JSONObject vaultObj = new JSONObject();
        vaultObj.put("vault",investObj);
        JSONArray jsonArray = loadFile(INVESTMENTS);
        jsonArray.add(vaultObj);
        saveFile(jsonArray,INVESTMENTS);
    }

    @Override
    public Investment readInvestment(long id) {
        if (investments == null) readAllInvestments();
        Investment investment = null;
        for (Investment vault : investments) {
            if (vault.getId() == id) investment = vault;
        }
        return investment;
    }

    @Override
    public void updateInvestment(long id, Investment investment) {
        JSONArray jsonArray = loadFile(INVESTMENTS);
        JSONObject investObj = new JSONObject();
        investObj.put("id",investment.getId());
        investObj.put("name",investment.getName());
        JSONArray containerArray = new JSONArray();
        for (IItem container : investment.getItems()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("capsule_id",container.getId());
            jsonObject.put("amount",investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put("capsule",jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put("capsules",containerArray);
        JSONObject vaultObj = new JSONObject();
        vaultObj.put("vault",investObj);
        for (Object o : jsonArray){
            JSONObject jsonObject = (JSONObject) o;
            JSONObject internalObject = (JSONObject) jsonObject.get("vault");
            if ((long)internalObject.get("id") == id){
                jsonArray.remove(jsonArray.indexOf(o));
                jsonArray.add(vaultObj);
                break;
            }
        }
        saveFile(jsonArray,INVESTMENTS);
    }

    @Override
    public void deleteInvestment(long id) {
        JSONArray jsonArray = loadFile(INVESTMENTS);
        for (Object o : jsonArray){
            JSONObject jsonObject = (JSONObject) o;
            JSONObject internal = (JSONObject) jsonObject.get("vault");
            if ((long)internal.get("id") == id){
                jsonArray.remove(jsonArray.indexOf(o));
                break;
            }
        }
        saveFile(jsonArray,INVESTMENTS);
    }

    @Override
    public List<IItem> readAllCapsules() {
        if (containers == null){
            containers = new ArrayList<>();
            JSONArray jsonArray = loadFile(CAPSULES);
            if (jsonArray != null) {
                for (Object o : jsonArray) {
                    IItem container = factory.emptyCapsule();
                    JSONObject jsonObject = (JSONObject) o;
                    JSONObject cap = (JSONObject) jsonObject.get(CAPSULE);
                    container.populate(
                            (long) cap.get("id"),
                            (double) cap.get("init_price"),
                            (String) cap.get("name"),
                            (String) cap.get("image"),
                            (String) cap.get("stash_link")
                    );
                    containers.add(container);
                }
            }
        }
        return containers;
    }

    @Override
    public void createCapsule(IItem container) {
        if (containers == null) readAllCapsules();
        containers.add(container);
        JSONObject jsonObject = new JSONObject();
        long maxId = findMaxContainerId() + 1;
        jsonObject.put("id",maxId);
        jsonObject.put("init_price",container.getInitPrice());
        jsonObject.put("name",container.getName());
        jsonObject.put("image",container.getImage());
        jsonObject.put("stash_link",container.getStashLink());
        JSONObject containerObj = new JSONObject();
        containerObj.put("capsule",jsonObject);
        JSONArray jsonArray = loadFile(CAPSULES);
        jsonArray.add(containerObj);
        saveFile(jsonArray, CAPSULES);
    }

    @Override
    public IItem readCapsule(long id) {
        if (containers == null) readAllCapsules();
        IItem container = null;
        for (IItem capsule : containers) {
            if (capsule.getId() == id){
                container = capsule;
            }
        }
        return container;
    }

    @Override
    public void updateCapsule(long id, IItem container) {
        JSONArray jsonArray = loadFile(CAPSULES);
        JSONObject containerObj = new JSONObject();
        containerObj.put("id",container.getId());
        containerObj.put("name",container.getName());
        containerObj.put("price",container.getInitPrice());
        containerObj.put("image",container.getImage());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("capsule",containerObj);
        for (Object o : jsonArray){
            JSONObject jObject = (JSONObject) o;
            JSONObject internalObject = (JSONObject) jObject.get("capsule");
            if ((long)internalObject.get("id") == id){
                jsonArray.remove(jsonArray.indexOf(o));
                jsonArray.add(jsonObject);
                break;
            }
        }
        saveFile(jsonArray, CAPSULES);
    }

    @Override
    public void deleteCapsule(long id) {
        System.out.println("NOT YET IMPLEMENTED");
    }
}
