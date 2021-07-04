package org.example.logic;

import org.example.logic.Interfaces.ICapsule;
import org.example.logic.Interfaces.IVault;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.example.Util.Attributes.*;

// FIXME: 05-07-2021 Add the ability to store Skin and Souvenir

public final class Vault implements IVault {

    private long id;
    private Map<ICapsule,Long> containers;
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
    public Map<ICapsule,Long> getAllContainers(){
        return containers;
    }

    @Override
    public void setAllItems(Map<ICapsule,Long> map){
        this.containers = map;
    }

    @Override
    public Set<ICapsule> getItems() {
        return containers.keySet();
    }

    @Override
    public void addItems(ICapsule capsule, long amount) {
        containers.put(capsule,amount);
    }

    @Override
    public void removeItem(ICapsule capsule) {
        containers.remove(capsule);
    }

    @Override
    public IVault populate(long id, String name){
        setId(id == -1 ? findMaxID() + 1 : id);
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
        for (ICapsule item : containers.keySet()){
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
        List<IVault> cache = Domain.getInstance().readAllInvestments();
        long maxValue = cache.get(0).getId();
        for (IVault investment : cache) {
            if (investment.getId() > maxValue) maxValue = investment.getId();
        }
        return maxValue;
    }
}
