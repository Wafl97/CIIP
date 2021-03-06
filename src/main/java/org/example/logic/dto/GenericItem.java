package org.example.logic.dto;

import org.example.logic.interfaces.dto.Item;
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

    protected boolean priceUpdated = false;

    public GenericItem(Attributes attributes){
        jsonAttribute = attributes.toString();
    }

    @Override
    public void setPriceUpdated(boolean overwrite) {
        this.priceUpdated = overwrite;
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
        if (!priceUpdated) updateCurrPrice();
        return currPrice;
    }

    @Override
    public void setCurrPrice(double price) {
        this.currPrice = price;
    }

    @Override
    public void updateCurrPrice() {
        if (!priceUpdated) {
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
            }
            setCurrPrice(d);
            priceUpdated = true;
        }
    }

    @Override
    public double getDiffPrice() {
        return initPrice - currPrice;
    }

    @Override
    public String getStashLink() {
        return link;
    }

    @Override
    public void setStashLink(String link) {
        this.link = link;
        updateCurrPrice();
    }

    @Override
    public JSONObject convert2JSON() {
        JSONObject shellObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),getId());
        innerObj.put(INIT_PRICE.toString(),getInitPrice());
        innerObj.put(NAME.toString(),getName());
        innerObj.put(IMAGE.toString(),getImage());
        innerObj.put(STASH_LINK.toString(),getStashLink());
        shellObj.put(jsonAttribute,innerObj);
        return shellObj;
    }
}
