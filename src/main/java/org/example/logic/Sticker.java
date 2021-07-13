package org.example.logic;

import org.example.logic.interfaces.ISticker;
import org.example.logic.interfaces.comps.Identifiable;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.Attributes.*;

public class Sticker implements ISticker {

    private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
    private static final Pattern STOP_PATTERN = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");



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
        long maxValue = 0;
        for (Identifiable sticker : Domain.getInstance().readAllStickers()){
            if (sticker.getId() > maxValue) maxValue = sticker.getId();
        }
        return maxValue;
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
        double d = 0.0d;
        try {
            Scanner input = new Scanner(new URL(link).openStream());
            String result = "";

            Matcher stopMatcher;
            //Skip to relevant
            while (input.hasNext()) {
                stopMatcher = STOP_PATTERN.matcher(input.nextLine());
                if (stopMatcher.find()){
                    result = input.nextLine();
                    break;
                }
            }
            input.close();
            Matcher priceMatcher = PRICE_PATTERN.matcher(result);
            if (priceMatcher.find()) {
                d = Double.parseDouble(priceMatcher.group(1).replace(",", "."));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currPrice = d;
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

    public static void main(String[] args) {
        ISticker sticker = StructureCreator.getInstance().emptySticker();
        sticker.setStashLink("https://csgostash.com/sticker/2657/Global-Elite-Foil");
        sticker.updateCurrPrice();
        System.out.println(sticker.getCurrPrice());
    }
}
