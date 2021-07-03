package org.example.logic.Interfaces;

public interface IItem<T> extends Convertible<T> {

    long getId();
    void setId(long id);

    double getInitPrice();
    void setInitPrice(double price);

    double getCurrPrice();
    void setCurrPrice(double price);

    void updateCurrPrice();
    double getDiffPrice();

    String getName();
    void setName(String name);

    String getImage();
    void setImage(String image);

    String getStashLink();
    void setStashLink(String link);

    T populate(long id, double initPrice, String name, String image, String stashLink);
}
