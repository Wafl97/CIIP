package org.example.logic;

import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Identifiable;
import org.example.logic.Interfaces.Investment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.example.Util.Attributes.*;

public final class Vault implements Investment<Vault>, Identifiable {

    private long id;
    private Map<IItem,Long> containers;
    private String name;

    public Vault() {
        containers = new HashMap<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<IItem,Long> getAllContainers(){
        return containers;
    }

    @Override
    public void setAllItems(Map<IItem,Long> map){
        this.containers = map;
    }

    @Override
    public Set<IItem> getItems() {
        return containers.keySet();
    }

    @Override
    public void addItems(IItem container, long amount) {
        containers.put(container,amount);
    }

    @Override
    public void removeItem(IItem container) {
        containers.remove(container);
    }

    @Override
    public Vault populate(long id, String name){
        setId(id);
        setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "Vault{" +
                "id=" + id +
                ", containers=" + containers +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public JSONObject convert2JSON() {
        //Create outer JSON obj
        JSONObject returnObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),this.id);
        innerObj.put(NAME.toString(),this.name);

        //Create the inner array
        JSONArray capsules = new JSONArray();
        for (IItem item : containers.keySet()){
            //Inner obj
            JSONObject capsule = new JSONObject();
            capsule.put(CAPSULE_ID.toString(), item.getId());
            capsule.put(AMOUNT.toString(), containers.get(item));
            //Shell obj
            JSONObject shell = new JSONObject();
            shell.put(CAPSULE.toString(), capsule);
            capsules.add(shell);
        }
        innerObj.put(CAPSULES.toString(),capsules);
        returnObj.put(VAULT.toString(),innerObj);
        return returnObj;
    }

    @Override
    public Vault convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(VAULT.toString());
        this.populate(
                (long)      innerObj.get(ID.toString()),
                (String)    innerObj.get(NAME.toString()));
        for (Object o : (JSONArray) innerObj.get(CAPSULES.toString())){
            JSONObject capsule = (JSONObject) ((JSONObject) o).get(CAPSULE.toString());
            containers.put(Domain.getInstance().readCapsule((long) capsule.get(CAPSULE_ID.toString())), (long) capsule.get(AMOUNT.toString()));
        }
        return this;
    }

    @Override
    public long findMaxID() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
