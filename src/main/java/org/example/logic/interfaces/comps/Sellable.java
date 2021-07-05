package org.example.logic.interfaces.comps;

public interface Sellable {

    double getInitPrice();
    void setInitPrice(double price);

    double getCurrPrice();
    void setCurrPrice(double price);

    void updateCurrPrice();
    double getDiffPrice();

    String getStashLink();
    void setStashLink(String link);
}
