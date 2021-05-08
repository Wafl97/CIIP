package org.example.logic;

public interface Container {

    int getId();
    void setId(int id);

    float getPrice();
    void setPrice(float price);

    String getName();
    void setName(String name);

    Container populate(int id, float price, String name);
}
