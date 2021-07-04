package org.example.logic.Interfaces.Comps;

public interface Sellable {

    double getInitPrice();
    void setInitPrice(double price);

    double getCurrPrice();
    void setCurrPrice(double price);

    void updateCurrPrice();
    double getDiffPrice();

}
