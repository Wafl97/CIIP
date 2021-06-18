package org.example.logic;

import org.example.logic.Interfaces.IItem;
import org.example.logic.Interfaces.Investment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Vault implements Investment {

    private long id;
    private Map<IItem,Long> containers = new HashMap<>();
    private String name;

    public Vault() {}

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
    public Investment populate(long id, String name){
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
}
