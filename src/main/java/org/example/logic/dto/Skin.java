package org.example.logic.dto;

import org.example.logic.dto.interfaces.ISkin;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.util.Attributes.*;

public final class Skin extends GenericItem<ISkin> implements ISkin {

    public Skin(){
        super(SKIN);
        SUB_DOMAIN = DOMAIN.getSkinDomain();
    }

    private boolean statTrak;
    private boolean souvenir;
    private double wearFloat;

    private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
    private Pattern wearPattern;

    @Override
    public void updateCurrPrice() {
        System.out.println(ConsoleColors.YELLOW + "Updating current price for" + ConsoleColors.RESET + " [" + ConsoleColors.BLUE + getName() + ConsoleColors.RESET + "] From: [" + getStashLink() + "]");
        Double[] prices = new Double[2];
        try {
            Scanner input = new Scanner(new URL(getStashLink()).openStream());
            String result;
            Matcher wearStopper;
            Matcher priceStopper;
            int pIndex = -2;

            while (input.hasNext()) {
                wearStopper = this.wearPattern.matcher(input.nextLine());
                if (wearStopper.find()) {
                    result = input.nextLine();
                    priceStopper = PRICE_PATTERN.matcher(result);
                    if (priceStopper.find() && pIndex >= 0) {
                        prices[pIndex] = Double.parseDouble(priceStopper.group(1).replace(",", ".").replace("-", "0"));
                    } else if (pIndex >= 0) {
                        prices[pIndex] = -1.0;
                    }
                    pIndex++;
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isStatTrak() || isSouvenir()) setCurrPrice(prices[0]);
        else setCurrPrice(prices[1]);
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
        if (wearFloat > 1 || wearFloat < -0) throw new IllegalArgumentException("Float has to be between 0 to 1");
        this.wearFloat = wearFloat;
        this.wearPattern = Pattern.compile( wearFloat <= 0.07 ? Wear.FACTORY_NEW.getRegex() :
                                            wearFloat <= 0.15 ? Wear.MINIMAL_WEAR.getRegex() :
                                            wearFloat <= 0.38 ? Wear.FIELD_TESTED.getRegex() :
                                            wearFloat <= 0.45 ? Wear.WELL_WORN.getRegex() :
                                                                Wear.BATTLE_SCARED.getRegex());
    }

    @Override
    public double getWearFloat() {
        return wearFloat;
    }


    @Override
    public JSONObject convert2JSON() {
        JSONObject innerObj = makeGenericJSON();
        innerObj.put(WEAR_FLOAT.toString(),getWearFloat());
        innerObj.put(STATTRAK.toString(), isStatTrak());
        innerObj.put(SOUVENIR.toString(),isSouvenir());
        return packageJSON(innerObj);
    }

    @Override
    public ISkin convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(jsonAttribute);
        return  populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (double)    innerObj.get(CURR_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString()),
                (double)    innerObj.get(WEAR_FLOAT.toString()),
                (boolean)   innerObj.get(STATTRAK.toString()),
                (boolean)   innerObj.get(SOUVENIR.toString())
        );
    }

    @Override
    public ISkin populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ISkin) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }

    @Override
    public ISkin populate(long id, double initPrice, double currPrice, String name, String image, String stashLink, double wearFloat, boolean statTrack, boolean souvenir) {
        setWearFloat(wearFloat);
        setStatTrak(statTrack);
        setSouvenir(souvenir);
        return populate(id,initPrice,currPrice,name,image,stashLink);
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
