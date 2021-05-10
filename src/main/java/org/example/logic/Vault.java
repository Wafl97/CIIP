package org.example.logic;

import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Investment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Vault implements Investment {

    private long id;
    private Map<Container,Long> containers;
    private String name;

    public Vault(long id, String name){
        this.id = id;
        this.name = name;
        containers = new HashMap<>();
    }

    public Vault(){
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
    public Map<Container,Long> getAllContainers(){
        return containers;
    }

    @Override
    public void setAllContainers(Map<Container,Long> map){
        this.containers = map;
    }

    @Override
    public Set<Container> getContainers() {
        return containers.keySet();
    }

    @Override
    public void addContainers(Container container, long amount) {
        containers.put(container,amount);
    }

    @Override
    public void removeContainers(Container container) {
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
