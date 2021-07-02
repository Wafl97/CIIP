package org.example.data;

import org.example.data.Interfaces.DataConnection;
import org.example.logic.Interfaces.*;
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

    //Data path
    private static final String PATH = "org/example/datacollection/";

    //File names
    private static final String INVESTMENTS = "investments";
    private static final String CAPSULES = "capsules";

    //Data attributes
    private static final String VAULT = "vault";
    private static final String CAPSULE = "capsule";
    private static final String ID = "id";
    private static final String INIT_PRICE = "init_price";
    private static final String NAME = "name";
    private static final String AMOUNT = "amount";
    private static final String IMAGE = "image";
    private static final String STASH_LINK = "stash_link";
    private static final String CAPSULE_ID = "capsule_id";

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
                            (long) vault.get(ID),
                            (String) vault.get(NAME)
                    );
                    JSONArray capArray = (JSONArray) vault.get(CAPSULES);
                    for (Object object : capArray) {
                        JSONObject jsonCaps = (JSONObject) object;
                        JSONObject cap = (JSONObject) jsonCaps.get(CAPSULE);
                        investment.addItems(readCapsule(
                                (long) cap.get(CAPSULE_ID)),
                                (long) cap.get(AMOUNT));
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
        investObj.put(ID,maxId);
        investObj.put(NAME,investment.getName());
        JSONArray containerArray = new JSONArray();
        for (IItem container : investment.getItems()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CAPSULE_ID,container.getId());
            jsonObject.put(AMOUNT,investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put(CAPSULE,jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put(CAPSULES,containerArray);
        JSONObject vaultObj = new JSONObject();
        vaultObj.put(VAULT,investObj);
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
    public void updateInvestment(Investment investment) {
        long id = investment.getId();
        JSONArray jsonArray = loadFile(INVESTMENTS);
        JSONObject investObj = new JSONObject();
        investObj.put(ID,investment.getId());
        investObj.put(NAME,investment.getName());
        JSONArray containerArray = new JSONArray();
        for (IItem container : investment.getItems()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CAPSULE_ID,container.getId());
            jsonObject.put(AMOUNT,investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put(CAPSULE,jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put(CAPSULES,containerArray);
        JSONObject vaultObj = new JSONObject();
        vaultObj.put(VAULT,investObj);
        for (Object o : jsonArray){
            JSONObject jsonObject = (JSONObject) o;
            JSONObject internalObject = (JSONObject) jsonObject.get(VAULT);
            if ((long)internalObject.get(ID) == id){
                jsonArray.remove(o);
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
            JSONObject internal = (JSONObject) jsonObject.get(VAULT);
            if ((long)internal.get(ID) == id){
                jsonArray.remove(o);
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
                            (long) cap.get(ID),
                            (double) cap.get(INIT_PRICE),
                            (String) cap.get(NAME),
                            (String) cap.get(IMAGE),
                            (String) cap.get(STASH_LINK)
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
        jsonObject.put(ID,maxId);
        jsonObject.put(INIT_PRICE,container.getInitPrice());
        jsonObject.put(NAME,container.getName());
        jsonObject.put(IMAGE,container.getImage());
        jsonObject.put(STASH_LINK,container.getStashLink());
        JSONObject containerObj = new JSONObject();
        containerObj.put(CAPSULE,jsonObject);
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
    public void updateCapsule(IItem container) {
        long id = container.getId();
        JSONArray jsonArray = loadFile(CAPSULES);
        JSONObject containerObj = new JSONObject();
        containerObj.put(ID,container.getId());
        containerObj.put(NAME,container.getName());
        containerObj.put(INIT_PRICE,container.getInitPrice());
        containerObj.put(IMAGE,container.getImage());
        containerObj.put(STASH_LINK,container.getStashLink());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CAPSULE,containerObj);
        for (Object o : jsonArray){
            JSONObject jObject = (JSONObject) o;
            JSONObject internalObject = (JSONObject) jObject.get(CAPSULE);
            if ((long)internalObject.get(ID) == id){
                jsonArray.remove(o);
                jsonArray.add(jsonObject);
                break;
            }
        }
        saveFile(jsonArray, CAPSULES);
    }

    @Override
    public void deleteCapsule(long id) {
        //Remove from investments
        JSONArray investArray = loadFile(INVESTMENTS);
        for (Object o : investArray){
            JSONObject investObj = (JSONObject) o;
            JSONObject innerObj = (JSONObject) investObj.get(VAULT);
            JSONArray innerInvestArray = (JSONArray) innerObj.get(CAPSULES);
            for (Object obj : innerInvestArray){
                JSONObject investCapsule = (JSONObject) obj;
                JSONObject finalObj = (JSONObject) investCapsule.get(CAPSULE);
                if ((long)finalObj.get(CAPSULE_ID) == id){
                    innerInvestArray.remove(obj);
                    break;
                }
            }
        }
        saveFile(investArray,INVESTMENTS);

        //Remove from capsules list
        JSONArray jsonArray = loadFile(CAPSULES);
        for (Object o : jsonArray){
            JSONObject capObj = (JSONObject) o;
            JSONObject finalObj = (JSONObject) capObj.get(CAPSULE);
            if ((long) finalObj.get(ID) == id){
                jsonArray.remove(o);
                break;
            }
        }
        saveFile(jsonArray,CAPSULES);
    }
}
