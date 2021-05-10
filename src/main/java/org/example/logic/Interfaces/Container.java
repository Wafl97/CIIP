package org.example.logic.Interfaces;

public interface Container {

    long getId();
    void setId(long id);

    double getPrice();
    void setPrice(double price);

    String getName();
    void setName(String name);

    String getImage();
    void setImage(String image);

    Container populate(long id, double price, String name, String image);
}
