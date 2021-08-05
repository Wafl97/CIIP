# CIIP
## CSGO Inventory Investment Program by

````text
 _       _____    ________ 
| |     / /   |  / ____/ / 
| | /| / / /| | / /_  / /  
| |/ |/ / ___ |/ __/ / /___
|__/|__/_/  |_/_/   /_____/
                           
````


This project is just for fun. 

It is for keeping track of investments made in CSGO.

Please enjoy :)


## How to use program

### How to create new items

### How to create and add items to vaults

## About the program

### Data storage
The program stores the data as JSON files

As for now the images use for the items are stored locally, this is planed to change to use the images from the CSGO Stash page.
The same is planed for the name and id.

#### Skins
````JSON
[
  {
    "SKIN": {
      "ID": 2048,
      "NAME": "AK-47 | Vulcan",
      "IMAGE": "AK47VULCAN.png",
      "INIT_PRICE": 435.0,
      "STASH_LINK": "https://csgostash.com/skin/354/AK-47-Vulcan",
      "STATTRAK": true,
      "SOUVENIR": false,
      "WEAR_FLOAT": 0.062461465597153
    }
  }
]
````

### Main program logic


#### How the current price it calculated

#### Variables
````java
private boolean statTrak;
private boolean souvenir;
private double wearFloat;

private static final Pattern PRICE_PATTERN = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
private Pattern wearPattern;
````

#### Enum
````java
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
````

#### Methods

Set the float
````java
public void setWearFloat(double wearFloat) {
    if (wearFloat > 1 || wearFloat < -0) throw new IllegalArgumentException("Float has to be between 0 to 1");
    this.wearFloat = wearFloat;
    this.wearPattern = Pattern.compile( wearFloat <= 0.07 ? Wear.FACTORY_NEW.getRegex() :
                                        wearFloat <= 0.15 ? Wear.MINIMAL_WEAR.getRegex() :
                                        wearFloat <= 0.38 ? Wear.FIELD_TESTED.getRegex() :
                                        wearFloat <= 0.45 ? Wear.WELL_WORN.getRegex() :
                                                            Wear.BATTLE_SCARED.getRegex());
}
````

Set StatTrak and Souvenir
````java
public void setStatTrak(boolean statTrak) {
    if (statTrak && isSouvenir()) throw new IllegalArgumentException("Cannot be StatTrack and Souvenir at the same time");
    this.statTrak = statTrak;
}
    
public void setSouvenir(boolean souvenir) {
    if (souvenir && isStatTrak()) throw new IllegalArgumentException("Cannot be Souvenir and StatTrack at the same time");
    this.souvenir = souvenir;
}
    
````


Calculate the price
````java
public void updateCurrPrice() {
    if (!priceUpdated) {
        System.out.println("Updating current price for [" + getName() + "] From: [" + getStashLink() + "]");
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
        if (isStatTrak() || isSouvenir()) setCurrPrice(prices[0]);
        else setCurrPrice(prices[1]);

    priceUpdated = true;
    }
}

````