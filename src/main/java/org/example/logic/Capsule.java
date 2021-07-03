package org.example.logic;

import org.example.logic.Interfaces.Identifiable;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Util.Attributes.*;

public final class Capsule extends Item<Capsule> implements Identifiable {

    private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,]+)(.)</span></a>");
    private static final Pattern STOP_PATTERN = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");

    public Capsule(){}

    @Override
    public void updateCurrPrice() {
        double d = 0.0d;
        try {
            Scanner input = new Scanner(new URL(link).openStream());
            StringBuffer result = new StringBuffer();

            Matcher stopMatcher;
            //Skip to relevant
            while (input.hasNext()) {
                stopMatcher = STOP_PATTERN.matcher(input.nextLine());
                if (stopMatcher.find()) result.append(input.nextLine());
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
    public JSONObject convert2JSON() {
        JSONObject shellObj = new JSONObject();
        JSONObject innerObj = new JSONObject();
        innerObj.put(ID.toString(),this.id);
        innerObj.put(INIT_PRICE.toString(),this.initPrice);
        innerObj.put(NAME.toString(),name);
        innerObj.put(IMAGE.toString(),image);
        innerObj.put(STASH_LINK.toString(),link);
        shellObj.put(CAPSULE.toString(),innerObj);
        return shellObj;
    }

    @Override
    public Capsule convert2Obj(JSONObject jsonObject) {
        JSONObject innerObj = (JSONObject) jsonObject.get(CAPSULE.toString());
        return  populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString())
        );
    }

    @Override
    public long findMaxID() {
        List<Capsule> cache = Domain.getInstance().readAllCapsules();
        long maxValue = cache.get(0).getId();
        for (Capsule capsule : cache) {
            if (capsule.getId() > maxValue) maxValue = capsule.getId();
        }
        return maxValue;
    }
}
