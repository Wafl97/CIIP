package org.example.logic;

public final class Capsule implements Container{

    private int id;
    private float price;
    private String name;

    public Capsule(int id, float price, String name){
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Capsule(){}

    @Override
    public Container populate(int id, float price, String name) {
        setId(id);
        setPrice(price);
        setName(name);
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
