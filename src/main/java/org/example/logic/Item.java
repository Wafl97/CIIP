package org.example.logic;

import org.example.logic.Interfaces.Container;

public final class Item implements Container {

    private long id;
    private double price;
    private String name;
    private String image;

    public Item(long id, double price, String name, String image){
        this.id = id;
        this.price = price;
        this.name = name;
        this.image = image;
    }

    public Item(){}

    @Override
    public Container populate(long id, double price, String name, String image) {
        setId(id);
        setPrice(price);
        setName(name);
        setImage(image);
        return this;
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
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
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

    @Override
    public String getImage(){
        return image;
    }

    @Override
    public void setImage(String image){
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
