package org.example.logic;

import org.example.logic.interfaces.ISkin;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.Attributes.*;

public final class Skin implements ISkin {

    private long id;
    private double initPrice;
    private double currPrice;
    private String name;
    private String image;
    private String link;
    private boolean statTrak;
    private boolean souvenir;
    private double wearFloat;

    private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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
// TODO: 13-07-2021 Optimise 
        Pattern wear = Pattern.compile( wearFloat <= 0.07 ? Wear.FACTORY_NEW.getRegex() :
                                        wearFloat <= 0.15 ? Wear.MINIMAL_WEAR.getRegex() :
                                        wearFloat <= 0.38 ? Wear.FIELD_TESTED.getRegex() :
                                        wearFloat <= 0.45 ? Wear.WELL_WORN.getRegex() :
                                                            Wear.BATTLE_SCARED.getRegex());
        Double[] prices = new Double[2];
        try {
            Scanner input = new Scanner(new URL(link).openStream());
            String result;
            Matcher wearStopper;
            Matcher priceStopper;
            int pIndex = -2;

            while (input.hasNext()) {
                wearStopper = wear.matcher(input.nextLine());
                if (wearStopper.find()){
                    result = input.nextLine();
                    priceStopper = PRICE_PATTERN.matcher(result);
                    if (priceStopper.find() && pIndex >= 0){
                        prices[pIndex] = Double.parseDouble(priceStopper.group(1).replace(",", ".").replace("-","0"));
                    }
                    else if (pIndex >= 0) {
                        prices[pIndex] = -1.0;
                    }
                    pIndex++;
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isStatTrak() || isSouvenir())   setCurrPrice(prices[0]);
        else                                setCurrPrice(prices[1]);
    }

    @Override
    public double getDiffPrice() {
        return initPrice - currPrice;
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
    public String getStashLink() {
        return link;
    }

    @Override
    public void setStashLink(String link) {
        this.link = link;
    }

    @Override
    public void setStatTrak(boolean statTrak) {
        if (statTrak && isSouvenir()) throw new IllegalArgumentException("Cannot be StatTrack and Souvenir at the same time");
        this.statTrak = statTrak;
    }

    @Override
    public boolean isStatTrak() {
        return statTrak;
    }

    @Override
    public void setSouvenir(boolean souvenir) {
        if (souvenir && isStatTrak()) throw new IllegalArgumentException("Cannot be Souvenir and StatTrack at the same time");
        this.souvenir = souvenir;
    }

    @Override
    public boolean isSouvenir() {
        return souvenir;
    }

    @Override
    public void setWearFloat(double wearFloat) {
        if (wearFloat > 1 || wearFloat < 0) throw new IllegalArgumentException("Float has to be between 0 to 1");
        this.wearFloat = wearFloat;
    }

    @Override
    public double getWearFloat() {
        return wearFloat;
    }

    @Override
    public ISkin populate(long id, double initPrice, String name, String image, String stashLink, double wearFloat, boolean statTrack, boolean souvenir) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        setWearFloat(wearFloat);
        setStatTrak(statTrack);
        setSouvenir(souvenir);
        return this;
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
        innerObj.put(WEAR_FLOAT.toString(),getWearFloat());
        innerObj.put(STAT_TRACK.toString(), isStatTrak());
        innerObj.put(SOUVENIR.toString(),isSouvenir());
        shellObj.put(SKIN.toString(),innerObj);
        return shellObj;
    }

    @Override
    public ISkin convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(SKIN.toString());
        return populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString()),
                (double)    innerObj.get(WEAR_FLOAT.toString()),
                (boolean)   innerObj.get(STAT_TRACK.toString()),
                (boolean)   innerObj.get(SOUVENIR.toString())
        );
    }

    @Override
    public long findMaxID() {
        List<ISkin> cache = Domain.getInstance().readAllSkins();
        long maxValue = cache.get(0).getId();
        for (ISkin skin : cache){
            if (skin.getId() > maxValue) maxValue = skin.getId();
        }
        return maxValue;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "id=" + id +
                ", initPrice=" + initPrice +
                ", currPrice=" + currPrice +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", statTrack=" + statTrak +
                ", souvenir=" + souvenir +
                ", wearFloat=" + wearFloat +
                '}';
    }

    private enum Wear {
        FACTORY_NEW("<span class=\"pull-left\">Factory New</span>"),
        MINIMAL_WEAR("<span class=\"pull-left\">Minimal Wear</span>"),
        FIELD_TESTED("<span class=\"pull-left\">Field-Tested</span>"),
        WELL_WORN("<span class=\"pull-left\">Well-Worn</span>"),
        BATTLE_SCARED("<span class=\"pull-left\">Battle-Scarred</span>");

        private final String regex;

        Wear(String regex){
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }
}
