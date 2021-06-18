package org.example.logic;

import org.example.logic.Interfaces.IItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Capsule extends Item {


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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currPrice = d;
    }

}
