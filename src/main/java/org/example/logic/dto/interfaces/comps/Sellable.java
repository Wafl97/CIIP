package org.example.logic.dto.interfaces.comps;

public interface Sellable {

    double getInitPrice();
    void setInitPrice(double price);

    double getCurrPrice();
    void setCurrPrice(double price);

    void updateCurrPrice();
    void setPriceUpdated(boolean overwrite);
    double getDiffPrice();

    String getStashLink();
    void setStashLink(String link);
}
