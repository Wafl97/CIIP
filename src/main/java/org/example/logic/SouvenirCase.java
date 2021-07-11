package org.example.logic;

import org.example.logic.interfaces.ISouvenirCase;
import org.json.simple.JSONObject;


// FIXME: 04-07-2021 
public final class SouvenirCase implements ISouvenirCase {

    private long id;
    private double initPrice;
    private double currPrice;
    private String name;
    private String image;
    private String link;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getStashLink() {
        return link;
    }

    @Override
    public void setStashLink(String link) {
        this.link = link;
    }

    @Override
    public ISouvenirCase populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }

    @Override
    public JSONObject convert2JSON() {
        return null;
    }

    @Override
    public ISouvenirCase convert2Obj(JSONObject jsonObject) {
        return null;
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
    public long findMaxID() {
        // FIXME: 07-07-2021
        return 0;
    }

    @Override
    public double getInitPrice() {
        return initPrice;
    }

    @Override
    public void setInitPrice(double price) {
        this.initPrice = price;
    }

    @Override
    public double getCurrPrice() {
        return currPrice;
    }

    @Override
    public void setCurrPrice(double price) {
        this.currPrice = price;
    }

    @Override
    public void updateCurrPrice() {
// FIXME: 07-07-2021
    }

    @Override
    public double getDiffPrice() {
        return currPrice - initPrice;
    }
}
