package org.example.logic;

import org.example.logic.Interfaces.ISkin;
import org.json.simple.JSONObject;

// FIXME: 05-07-2021
public final class Skin implements ISkin {

    private boolean stat_track;
    private boolean souvenir;
    private float wear_float;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

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
    public ISkin populate(long id, double initPrice, String name, String image, String stashLink) {
        return null;
    }


    @Override
    public JSONObject convert2JSON() {
        return null;
    }

    @Override
    public ISkin convert2Obj(JSONObject jsonObject) {
        return null;
    }

    @Override
    public long findMaxID() {
        return 0;
    }

    @Override
    public void setStatTrack(boolean statTrack) {

    }

    @Override
    public boolean isStatTrack() {
        return false;
    }

    @Override
    public void setSouvenir(boolean souvenir) {

    }

    @Override
    public boolean isSouvenir() {
        return false;
    }

    @Override
    public void setWearFloat(float wearFloat) {

    }

    @Override
    public float getWearFloat() {
        return 0;
    }
}
