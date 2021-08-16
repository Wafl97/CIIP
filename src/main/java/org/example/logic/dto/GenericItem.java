package org.example.logic.dto;

import org.example.logic.dto.interfaces.Item;
import org.example.util.Attributes;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.Attributes.*;

public abstract class GenericItem<T> implements Item<T> {

    private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
    private static final Pattern STOP_PATTERN = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");

    protected String jsonAttribute;

    protected long id;
    protected double initPrice;
    protected double currPrice;
    protected String name;
    protected String image;
    protected String link;

    public GenericItem(Attributes attributes){
        jsonAttribute = attributes.toString();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id == -1 ? findMaxID() + 1 : id;
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
        System.out.println(ConsoleColors.YELLOW + "Updating current price for" + ConsoleColors.RESET + " [" + ConsoleColors.BLUE + getName() + ConsoleColors.RESET + "] From: [" + getStashLink() + "]");
        double d = 0.0d;
        try {
            Scanner input = new Scanner(new URL(link).openStream());
            String result = "";

            Matcher stopMatcher;
            //Skip to relevant
            while (input.hasNext()) {
                stopMatcher = STOP_PATTERN.matcher(input.nextLine());
                if (stopMatcher.find()) {
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
            throw new IllegalStateException("Cant use the given url");
        }
        setCurrPrice(d);
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
    public JSONObject convert2JSON() {
        JSONObject shellObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),getId());
        innerObj.put(INIT_PRICE.toString(),getInitPrice());
        innerObj.put(CURR_PRICE.toString(),getCurrPrice());
        innerObj.put(NAME.toString(),getName());
        innerObj.put(IMAGE.toString(),getImage());
        innerObj.put(STASH_LINK.toString(),getStashLink());
        shellObj.put(jsonAttribute,innerObj);
        return shellObj;
    }

    protected GenericItem popHelper(long id, double initPrice, double currPrice, String name, String image, String stashLink){
        setId(id);
        setInitPrice(initPrice);
        setCurrPrice(currPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }

    protected GenericItem convertHelper(JSONObject jsonObject){
        JSONObject innerObj = (JSONObject) jsonObject.get(jsonAttribute);
        return popHelper(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (double)    innerObj.get(CURR_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString())
        );
    }

    @Override
    public String toString() {
        return "GenericItem{" +
                "id=" + id +
                ", initPrice=" + initPrice +
                ", currPrice=" + currPrice +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
