package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.Item;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.logic.interfaces.IGenericDomain;
import org.example.logic.interfaces.Logic;
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

    protected static final Logic DOMAIN = Domain.getInstance();

    protected static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
    protected static final Pattern STOP_PATTERN = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");

    protected String jsonAttribute;
    protected IGenericDomain<T> SUB_DOMAIN;

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
        this.currPrice = Math.max(price, 0.0);
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
                d = Double.parseDouble(priceMatcher.group(1).replace(",", ".").replace("-","0"));
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
        JSONObject innerObj = makeGenericJSON();
        return packageJSON(innerObj);
    }

    protected GenericItem<T> popHelper(long id, double initPrice, double currPrice, String name, String image, String stashLink){
        setId(id);
        setInitPrice(initPrice);
        setCurrPrice(currPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }

    protected GenericItem<T> convertHelper(JSONObject jsonObject){
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

    @SuppressWarnings({"unchecked"})
    protected JSONObject makeGenericJSON(){
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),getId());
        innerObj.put(INIT_PRICE.toString(),getInitPrice());
        innerObj.put(CURR_PRICE.toString(),getCurrPrice());
        innerObj.put(NAME.toString(),getName());
        innerObj.put(IMAGE.toString(),getImage());
        innerObj.put(STASH_LINK.toString(),getStashLink());
        return innerObj;
    }

    @SuppressWarnings({"unchecked"})
    protected JSONObject packageJSON(JSONObject jsonObject){
        JSONObject shell = new JSONObject();
        shell.put(jsonAttribute,jsonObject);
        return shell;
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Transferable<T> item : SUB_DOMAIN.readAll()){
            if (item.getId() > maxValue) maxValue = item.getId();
        }
        return maxValue;
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
