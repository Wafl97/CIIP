package org.example.logic;

import org.example.logic.interfaces.ISticker;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public class Sticker implements ISticker {

    private long id;
    private double initPrice;
    private double currPrice;
    private String name;
    private String image;
    private String link;

    @Override
    public ISticker populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }

    @Override
    public JSONObject convert2JSON() {
        JSONObject shellObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),this.id);
        innerObj.put(INIT_PRICE.toString(),this.initPrice);
        innerObj.put(NAME.toString(),name);
        innerObj.put(IMAGE.toString(),image);
        innerObj.put(STASH_LINK.toString(),link);
        shellObj.put(STICKER.toString(),innerObj);
        return shellObj;
    }

    @Override
    public ISticker convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(STICKER.toString());
        return  populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString())
        );
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
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
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
        // TODO: 13-07-2021
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
        // TODO: 13-07-2021
    }

    @Override
    public double getDiffPrice() {
        return currPrice - initPrice;
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
    public String toString() {
        return "Sticker{" +
                "id=" + getId() +
                ", initPrice=" + getInitPrice() +
                ", currPrice=" + getCurrPrice() +
                ", name='" + getName() + '\'' +
                ", image='" + getImage() + '\'' +
                ", link='" + getStashLink() + '\'' +
                '}';
    }
}
