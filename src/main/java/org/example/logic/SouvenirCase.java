package org.example.logic;

import org.example.logic.interfaces.ISouvenirCase;
import org.json.simple.JSONObject;


// FIXME: 04-07-2021 
public final class SouvenirCase implements ISouvenirCase {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getImage() {
        return null;
    }

    @Override
    public void setImage(String image) {

    }

    @Override
    public String getStashLink() {
        return null;
    }

    @Override
    public void setStashLink(String link) {

    }

    @Override
    public ISouvenirCase populate(long id, double initPrice, String name, String image, String stashLink) {
        return null;
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
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public long findMaxID() {
        return 0;
    }

    @Override
    public double getInitPrice() {
        return 0;
    }

    @Override
    public void setInitPrice(double price) {

    }

    @Override
    public double getCurrPrice() {
        return 0;
    }

    @Override
    public void setCurrPrice(double price) {

    }

    @Override
    public void updateCurrPrice() {

    }

    @Override
    public double getDiffPrice() {
        return 0;
    }
}
