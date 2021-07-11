package org.example.logic;

import org.example.logic.interfaces.ISkin;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.regex.Pattern;

import static org.example.util.Attributes.*;

public final class Skin implements ISkin {

    private long id;
    private double initPrice;
    private double currPrice;
    private String name;
    private String image;
    private String link;
    private boolean statTrack;
    private boolean souvenir;
    private double wearFloat;

    //Normal
    // TODO: 11-07-2021
    private static final Pattern FN_STOP_PATTERN = Pattern.compile("");
    private static final Pattern MW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern FT_STOP_PATTERN = Pattern.compile("");
    private static final Pattern WW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern BS_STOP_PATTERN = Pattern.compile("");

    //StatTrack
    // TODO: 11-07-2021
    private static final Pattern ST_FN_STOP_PATTERN = Pattern.compile("");
    private static final Pattern ST_MW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern ST_FT_STOP_PATTERN = Pattern.compile("");
    private static final Pattern ST_WW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern ST_BS_STOP_PATTERN = Pattern.compile("");

    //Souvenir
    // TODO: 11-07-2021
    private static final Pattern SV_FN_STOP_PATTERN = Pattern.compile("");
    private static final Pattern SV_MW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern SV_FT_STOP_PATTERN = Pattern.compile("");
    private static final Pattern SV_WW_STOP_PATTERN = Pattern.compile("");
    private static final Pattern SV_BS_STOP_PATTERN = Pattern.compile("");

    // TODO: 11-07-2021
    private static final Pattern PRICE_PATTERN = Pattern.compile("");

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
// FIXME: 11-07-2021
        if (statTrack) {
            System.out.print("ST ");
            if (wearFloat <= 0.07d) {           //Factory New
                System.out.println("FN");
            } else if (wearFloat <= 0.15d) {    //Minimal Wear
                System.out.println("MW");
            } else if (wearFloat <= 0.38d) {    //Field Tested
                System.out.println("FT");
            } else if (wearFloat <= 0.45d) {    //Well Worn
                System.out.println("WW");
            } else {                            //Battle Scared
                System.out.println("BS");
            }
        }
        else if (souvenir){
            System.out.print("SV ");
            if (wearFloat <= 0.07d) {           //Factory New
                System.out.println("FN");
            } else if (wearFloat <= 0.15d) {    //Minimal Wear
                System.out.println("MW");
            } else if (wearFloat <= 0.38d) {    ///Field Tested
                System.out.println("FT");
            } else if (wearFloat <= 0.45d) {    ///Well Worn
                System.out.println("WW");
            } else {                            //Battle Scared
                System.out.println("BS");
            }
        }
        else {
            System.out.print("NM ");
            if (wearFloat <= 0.07d) {           //Factory New
                System.out.println("FN");
            } else if (wearFloat <= 0.15d) {    //Minimal Wear
                System.out.println("MW");
            } else if (wearFloat <= 0.38d) {    //Field Tested
                System.out.println("FT");
            } else if (wearFloat <= 0.45d) {    //Well Worn
                System.out.println("WW");
            } else {                            //Battle Scared
                System.out.println("BS");
            }
        }
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
    public void setStatTrack(boolean statTrack) {
        if (statTrack && isSouvenir()) throw new IllegalArgumentException("Cannot be StatTrack and Souvenir at the same time");
        this.statTrack = statTrack;
    }

    @Override
    public boolean isStatTrack() {
        return statTrack;
    }

    @Override
    public void setSouvenir(boolean souvenir) {
        if (souvenir && isStatTrack()) throw new IllegalArgumentException("Cannot be Souvenir and StatTrack at the same time");
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
        setStatTrack(statTrack);
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
        innerObj.put(STAT_TRACK.toString(),isStatTrack());
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
                ", statTrack=" + statTrack +
                ", souvenir=" + souvenir +
                ", wearFloat=" + wearFloat +
                '}';
    }
}
