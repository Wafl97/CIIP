package org.example.util;

public enum Attributes {
    AMOUNT("Amount"),
    CAPSULE("Capsule"),
    CAPSULES("Capsules"),
    CASE("Case"),
    CASES("Cases"),
    CURR_PRICE("CurrentPrice"),
    GRAFFITI("Graffiti"),
    GRAFFITIES("Graffities"),
    ID("ID"),
    IMAGE("Image"),
    INIT_PRICE("InitialPrice"),
    KEY("Key"),
    KEYS("Keys"),
    MUSICKIT("MusicKit"),
    MUSICKITS("MusicKits"),
    NAME("Name"),
    PATCH("Patch"),
    PATCHES("Patches"),
    PIN("Pin"),
    PINS("Pins"),
    PLAYERMODEL("PlayerModel"),
    PLAYERMODELS("PlayerModeles"),
    SKIN("Skin"),
    SKINS("Skins"),
    SOUVENIR("Souvenir"),
    SOUVENIRS("Souvenirs"),
    STASH_LINK("StashLink"),
    STATTRAK("StatTrak"),
    STICKER("Sticker"),
    STICKERS("Stickers"),
    TICKET("Ticker"),
    TICKETS("Tickets"),
    VAULT("Vault"),
    VAULTS("Vaults"),
    WEAR_FLOAT("WearFloat");

    Attributes(String norm) {
        this.norm = norm;
    }

    private final String norm;

    public String toNorm(){
        return norm;
    }
}
