package org.example.data;

import org.example.logic.Interfaces.Container;
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
    private static final String CONTAINERS = "containers";
    private static final String CONTAINER = "container";

    private static JsonConnection instance;
    private List<Investment> investments;
    private List<Container> containers;
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
                    JSONArray capArray = (JSONArray) vault.get(CONTAINERS);
                    for (Object object : capArray) {
                        JSONObject jsonCaps = (JSONObject) object;
                        JSONObject cap = (JSONObject) jsonCaps.get(CONTAINER);
                        investment.addContainers(readContainer((long) cap.get("container_id")), (long) cap.get("amount"));
                    }
                    investments.add(investment);
                }
            }
        }
        return investments;
    }

    @Override
    public void createInvestment(Investment investment) {
        if (investments == null) readAllContainers();
        investments.add(investment);
        JSONObject investObj = new JSONObject();
        investObj.put("id",investment.getId());
        investObj.put("name",investment.getName());
        JSONArray containerArray = new JSONArray();
        for (Container container : investment.getContainers()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("container_id",container.getId());
            jsonObject.put("amount",investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put("container",jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put("containers",containerArray);
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
        for (Container container : investment.getContainers()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("container_id",container.getId());
            jsonObject.put("amount",investment.getAllContainers().get(container));
            JSONObject containerObj = new JSONObject();
            containerObj.put("container",jsonObject);
            containerArray.add(containerObj);
        }
        investObj.put("containers",containerArray);
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
    public List<Container> readAllContainers() {
        if (containers == null){
            containers = new ArrayList<>();
            JSONArray jsonArray = loadFile(CONTAINERS);
            if (jsonArray != null) {
                for (Object o : jsonArray) {
                    Container container = factory.emptyCapsule();
                    JSONObject jsonObject = (JSONObject) o;
                    JSONObject cap = (JSONObject) jsonObject.get(CONTAINER);
                    container.populate(
                            (long) cap.get("id"),
                            (double) cap.get("price"),
                            (String) cap.get("name"),
                            (String) cap.get("image")
                    );
                    containers.add(container);
                }
            }
        }
        return containers;
    }

    @Override
    public void createContainer(Container container) {
        if (containers == null) readAllContainers();
        containers.add(container);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",container.getId());
        jsonObject.put("price",container.getPrice());
        jsonObject.put("name",container.getName());
        jsonObject.put("image",container.getImage());
        JSONObject containerObj = new JSONObject();
        containerObj.put("container",jsonObject);
        JSONArray jsonArray = loadFile(CONTAINERS);
        jsonArray.add(containerObj);
        saveFile(jsonArray,CONTAINERS);
    }

    @Override
    public Container readContainer(long id) {
        if (containers == null) readAllContainers();
        Container container = null;
        for (Container capsule : containers) {
            if (capsule.getId() == id){
                container = capsule;
            }
        }
        return container;
    }

    @Override
    public void updateContainer(long id, Container container) {
        JSONArray jsonArray = loadFile(CONTAINERS);
        JSONObject containerObj = new JSONObject();
        containerObj.put("id",container.getId());
        containerObj.put("name",container.getName());
        containerObj.put("price",container.getPrice());
        containerObj.put("image",container.getImage());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("container",containerObj);
        for (Object o : jsonArray){
            JSONObject jObject = (JSONObject) o;
            JSONObject internalObject = (JSONObject) jObject.get("container");
            if ((long)internalObject.get("id") == id){
                jsonArray.remove(jsonArray.indexOf(o));
                jsonArray.add(jsonObject);
                break;
            }
        }
        saveFile(jsonArray,CONTAINERS);
    }

    @Override
    public void deleteContainer(long id) {
        System.out.println("NOT YET IMPLEMENTED");
    }
}
