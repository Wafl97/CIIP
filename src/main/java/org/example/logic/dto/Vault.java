package org.example.logic.dto;

import org.example.logic.interfaces.dto.*;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Identifiable;
import org.example.logic.interfaces.sub.*;
import org.example.logic.sub.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.example.util.Attributes.*;

public final class Vault implements IVault {

    private long id;
    private Map<Displayable,Long> containers;
    private String name;

    private static final IVaultDomain VAULT_DOMAIN = VaultDomain.getInstance();
    private static final ICapsuleDomain CAPSULE_DOMAIN = CapsuleDomain.getInstance();
    private static final IStickerDomain STICKER_DOMAIN = StickerDomain.getInstance();
    private static final ISkinDomain SKIN_DOMAIN = SkinDomain.getInstance();
    private static final ISouvenirCaseDomain SOUVENIR_CASE_DOMAIN = SouvenirCaseDomain.getInstance();
    private static final IPatchDomain PATCH_DOMAIN = PatchDomain.getInstance();
    private static final ICaseDomain CASE_DOMAIN = CaseDomain.getInstance();
    private static final ITicketDomain TICKET_DOMAIN = TicketDomain.getInstance();

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
        JSONArray stickers = new JSONArray();
        JSONArray souvenirs = new JSONArray();
        JSONArray patches = new JSONArray();
        JSONArray cases = new JSONArray();
        JSONArray tickets = new JSONArray();
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
            else if (item instanceof ISticker){
                JSONObject sticker = new JSONObject();
                sticker.put(ID.toString(), item.getId());
                sticker.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(STICKER.toString(), sticker);
                stickers.add(shell);
            }
            else if (item instanceof ISouvenirCase){
                JSONObject souvenir = new JSONObject();
                souvenir.put(ID.toString(), item.getId());
                souvenir.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(SOUVENIR.toString(), souvenir);
                souvenirs.add(shell);
            }
            else if (item instanceof IPatch){
                JSONObject patch = new JSONObject();
                patch.put(ID.toString(), item.getId());
                patch.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(PATCH.toString(), patch);
                patches.add(shell);
            }
            else if (item instanceof ICase){
                JSONObject _case = new JSONObject();
                _case.put(ID.toString(), item.getId());
                _case.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(CASE.toString(), _case);
                cases.add(shell);
            }
            else if (item instanceof ITicket){
                JSONObject ticket = new JSONObject();
                ticket.put(ID.toString(), item.getId());
                ticket.put(AMOUNT.toString(), containers.get(item));
                JSONObject shell = new JSONObject();
                shell.put(TICKET.toString(), ticket);
                tickets.add(shell);
            }
        }
        innerObj.put(CAPSULES.toString(),capsules);
        innerObj.put(SKINS.toString(),skins);
        innerObj.put(STICKERS.toString(),stickers);
        innerObj.put(SOUVENIRS.toString(),souvenirs);
        innerObj.put(PATCHES.toString(),patches);
        innerObj.put(CASES.toString(),cases);
        innerObj.put(TICKETS.toString(),tickets);
        returnObj.put(VAULT.toString(),innerObj);
        return returnObj;
    }

    @Override
    public Vault convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(VAULT.toString());
        this.populate(
                (long)      innerObj.get(ID.toString()),
                (String)    innerObj.get(NAME.toString()));
        //Add Capsules
        for (Object o : (JSONArray) innerObj.get(CAPSULES.toString())){
            JSONObject capsule = (JSONObject) ((JSONObject) o).get(CAPSULE.toString());
            containers.put(CAPSULE_DOMAIN.readCapsule((long) capsule.get(ID.toString())), (long) capsule.get(AMOUNT.toString()));
        }
        //Add Skins
        for (Object o : (JSONArray) innerObj.get(SKINS.toString())){
            JSONObject skin = (JSONObject) ((JSONObject) o).get(SKIN.toString());
            containers.put(SKIN_DOMAIN.readSkin((long) skin.get(ID.toString())), (long) skin.get(AMOUNT.toString()));
        }
        //Add Stickers
        for (Object o : (JSONArray) innerObj.get(STICKERS.toString())){
            JSONObject sticker = (JSONObject) ((JSONObject) o).get(STICKER.toString());
            containers.put(STICKER_DOMAIN.readSticker((long) sticker.get(ID.toString())), (long) sticker.get(AMOUNT.toString()));
        }
        //Add SouvenirCases
        for (Object o : (JSONArray) innerObj.get(SOUVENIRS.toString())){
            JSONObject souvenir = (JSONObject) ((JSONObject) o).get(SOUVENIR.toString());
            containers.put(SOUVENIR_CASE_DOMAIN.readSouvenirCase((long) souvenir.get(ID.toString())), (long) souvenir.get(AMOUNT.toString()));
        }
        //Add Patches
        for (Object o : (JSONArray) innerObj.get(PATCHES.toString())){
            JSONObject patch = (JSONObject) ((JSONObject) o).get(PATCH.toString());
            containers.put(PATCH_DOMAIN.readPatch((long) patch.get(ID.toString())), (long) patch.get(AMOUNT.toString()));
        }
        //Add Cases
        for (Object o : (JSONArray) innerObj.get(CASES.toString())){
            JSONObject _case = (JSONObject) ((JSONObject) o).get(CASE.toString());
            containers.put(CASE_DOMAIN.readCase((long) _case.get(ID.toString())), (long) _case.get(AMOUNT.toString()));
        }
        //Add Tickets
        for (Object o : (JSONArray) innerObj.get(TICKETS.toString())){
            JSONObject ticket = (JSONObject) ((JSONObject) o).get(TICKET.toString());
            containers.put(TICKET_DOMAIN.readTicket((long) ticket.get(ID.toString())), (long) ticket.get(AMOUNT.toString()));
        }
        return this;
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable investment : VAULT_DOMAIN.readAllVaults()) {
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
