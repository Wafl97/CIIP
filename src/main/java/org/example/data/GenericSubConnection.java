package org.example.data;

import org.example.data.interfaces.IGenericSubConnection;
import org.example.util.Attributes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.example.util.Attributes.*;

public final class GenericSubConnection implements IGenericSubConnection {

    private final Attributes TABLE;
    private final Attributes ATTRIBUTE;
    private final boolean cascadeOnDelete;

    public GenericSubConnection(Attributes TABLE, Attributes ATTRIBUTE, boolean cascadeOnDelete){
        this.TABLE = TABLE;
        this.ATTRIBUTE = ATTRIBUTE;
        this.cascadeOnDelete = cascadeOnDelete;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<JSONObject> readAll() {
        return new ArrayList<JSONObject>(Objects.requireNonNull(loadFile(TABLE)));
    }

    @Override
    public boolean create(JSONObject jsonObject) {
        return addObjToTable(jsonObject,TABLE);
    }

    @Override
    public JSONObject read(long id) {
        return readOneObj(id,readAll(),ATTRIBUTE);
    }

    @Override
    public boolean update(JSONObject jsonObject) {
        return updateObjInTable(jsonObject,TABLE,ATTRIBUTE);
    }

    @Override
    public boolean delete(long id) {
        return removeObjFromTable(id,TABLE,ATTRIBUTE,cascadeOnDelete);
    }

    //==================================================================================================================
    //Helper methods

    private boolean saveFile(JSONArray jsonArray, Attributes file){
        File jsonFile = JsonConnection.getInstance().getFileMap().get(file.toString());
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JSONArray loadFile(Attributes file){
        try(FileReader reader = new FileReader(JsonConnection.getInstance().getFileMap().get(file.toString()).getPath())) {
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

    @SuppressWarnings({"unchecked"})
    private boolean addObjToTable(JSONObject obj, Attributes table){
        JSONArray array = loadFile(table);
        assert array != null;
        array.add(obj);
        return saveFile(array,table);
    }

    private boolean removeObjFromTable(long id, Attributes table, Attributes objName, boolean cascade) {
        boolean finalRtn = false;

        JSONArray jsonArray = loadFile(table);
        if (jsonArray == null) {
            throw new NoSuchElementException();
        }
        for (Object o : jsonArray) {
            JSONObject outerObj = (JSONObject) o;
            JSONObject innerObj = (JSONObject) outerObj.get(objName.toString());
            if ((long) innerObj.get(ID.toString()) == id) {
                jsonArray.remove(o);
                break;
            }
            finalRtn = saveFile(jsonArray, table);
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
                finalRtn = saveFile(jsonArray, table);
            }
        }
        return finalRtn;
    }

    @SuppressWarnings({"unchecked"})
    private boolean updateObjInTable(JSONObject jsonObject, Attributes table, Attributes objName) {
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
            return saveFile(jsonArray, table);
        }
        return false;
    }
}
