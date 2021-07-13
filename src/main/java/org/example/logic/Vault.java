package org.example.logic;

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.comps.Identifiable;
import org.example.logic.interfaces.ICapsule;
import org.example.logic.interfaces.ISkin;
import org.example.logic.interfaces.IVault;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.example.util.Attributes.*;

// FIXME: 05-07-2021 Add the ability to store Skin and Souvenir

public final class Vault implements IVault {

    private long id;
    private Map<Displayable,Long> containers;
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
    public Map<Displayable,Long> getAllItems(){
        return containers;
    }

    @Override
    public void setAllItems(Map<Displayable,Long> map){
        this.containers = map;
    }

    @Override
    public Set<Displayable> getItems() {
        return containers.keySet();
    }

    @Override
    public void addItems(Displayable capsule, long amount) {
        containers.put(capsule,amount);
    }

    @Override
    public void removeItem(Displayable capsule) {
        containers.remove(capsule);
    }

    @Override
    public IVault populate(long id, String name){
        setId(id == -1 ? findMaxID() + 1 : id);
        setName(name);
        return this;
    }

    @Override
    public JSONObject convert2JSON() {
        //Create outer JSON obj
        JSONObject returnObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),getId());
        innerObj.put(NAME.toString(),getName());

        //Create the inner array
        JSONArray capsules = new JSONArray();
        JSONArray skins = new JSONArray();
        for (Identifiable item : containers.keySet()){
            //Inner obj
            if (item instanceof ICapsule) {
                JSONObject capsule = new JSONObject();
                capsule.put(ID.toString(), item.getId());
                capsule.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(CAPSULE.toString(), capsule);
                capsules.add(shell);
            }
            else if (item instanceof ISkin){
                JSONObject skin = new JSONObject();
                skin.put(ID.toString(), item.getId());
                skin.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(SKIN.toString(), skin);
                skins.add(shell);
            }
        }
        innerObj.put(CAPSULES.toString(),capsules);
        innerObj.put(SKINS.toString(),skins);
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
            containers.put(Domain.getInstance().readCapsule((long) capsule.get(ID.toString())), (long) capsule.get(AMOUNT.toString()));
        }
        for (Object o : (JSONArray) innerObj.get(SKINS.toString())){
            JSONObject skin = (JSONObject) ((JSONObject) o).get(SKIN.toString());
            containers.put(Domain.getInstance().readSkin((long) skin.get(ID.toString())), (long) skin.get(AMOUNT.toString()));
        }
        return this;
    }

    @Override
    public long findMaxID() {
        List<IVault> cache = Domain.getInstance().readAllVaults();
        long maxValue = cache.get(0).getId();
        for (IVault investment : cache) {
            if (investment.getId() > maxValue) maxValue = investment.getId();
        }
        return maxValue;
    }

    @Override
    public String toString() {
        return "Vault{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", containers=" + getAllItems() +
                '}';
    }
}
