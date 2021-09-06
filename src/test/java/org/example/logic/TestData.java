package org.example.logic;

import org.example.logic.dto.*;
import org.example.logic.dto.interfaces.*;

public class TestData {

    //Capsules
    public static ICapsule a1 = new Capsule().populate(1,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
    public static ICapsule a2 = new Capsule().populate(2,0.22,0.0,"Berlin 2019 Minor","2019BER_MIN.png","https://csgostash.com/stickers/capsule/280/Berlin-2019-Minor-Challengers-Holo-Foil");
    public static ICapsule a3 = new Capsule().populate(3,0.22,0.0,"Berlin 2019 Returning","2019BER_RET.png","https://csgostash.com/stickers/capsule/279/Berlin-2019-Returning-Challengers-Holo-Foil");
    public static ICapsule a4 = new Capsule().populate(4,0.2,0.0,"RMR 2020 Challengers","2020RMR_CHA.png","https://csgostash.com/stickers/capsule/312/2020-RMR-Challengers");
    public static ICapsule a5 = new Capsule().populate(5,0.2,0.0,"RMR 2020 Contenders","2020RMR_CON.png","https://csgostash.com/stickers/capsule/313/2020-RMR-Contenders");
    public static ICapsule a6 = new Capsule().populate(6,0.2,0.0,"RMR 2020 Legends","2020RMR_LEG.png","https://csgostash.com/stickers/capsule/311/2020-RMR-Legends");

    //Cases
    // TODO: 16-08-2021 Add more Cases to test data
    public static ICase b1 = new Case().populate(1,156.22,0.0,"Operation Hydra Case","CASE_HYDRA.png","https://csgostash.com/case/208/Operation-Hydra-Case");
    /*
    ICase b2 = new Case().populate
    ICase b3 = new Case().populate
    ICase b4 = new Case().populate
    ICase b5 = new Case().populate
    ICase b6 = new Case().populate
    */

    //Graffities
    // TODO: 16-08-2021 Add more Graffities to test data
    public static IGraffiti c1 = new Graffiti().populate(1,1.22,0.0,"Rising Skull","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
    /*
    IGraffiti c2 = new Graffiti().populate
    IGraffiti c3 = new Graffiti().populate
    IGraffiti c4 = new Graffiti().populate
    IGraffiti c5 = new Graffiti().populate
    IGraffiti c6 = new Graffiti().populate
    */

    //Keys
    // TODO: 30-08-2021 Add more Keys to test data
    public static IKey d1 = new Key().populate(1,2.5,0.0,"Operation Hydra Case Key","KEY_HYDRA.png","https://csgostash.com/item/9791/Operation-Hydra-Case-Key");
    /*
    IKey d2 = new Key().populate
    IKey d3 = new Key().populate
    IKey d4 = new Key().populate
    IKey d5 = new Key().populate
    IKey d6 = new Key().populate
    */

    //MusicKits
    // TODO: 30-08-2021 Add more MusicKits to test data
    public static IMusicKit e1 = new MusicKit().populate(1,25.0,0.0,"Crimson Assault","MUSICKIT_1.png","https://csgostash.com/music/2/Crimson-Assault-Daniel-Sadowski");
    /*
    IMusicKit e2 = new MusicKit().populate
    IMusicKit e3 = new MusicKit().populate
    IMusicKit e4 = new MusicKit().populate
    IMusicKit e5 = new MusicKit().populate
    IMusicKit e6 = new MusicKit().populate
    */

    //Patches
    // TODO: 30-08-2021 Add more Patches to test data
    public static IPatch f1 = new Patch().populate(1,156.22,0.0,"Patch | Crazy Banana","Banana patch.png","https://csgostash.com/patch/9/Crazy-Banana");
    /*
    IPatch f2 = new Patch().populate
    IPatch f3 = new Patch().populate
    IPatch f4 = new Patch().populate
    IPatch f5 = new Patch().populate
    IPatch f6 = new Patch().populate
    */

    //Pins
    // TODO: 30-08-2021 Add more Pins to test data
    public static IPin g1 = new Pin().populate(1,60.60,0.0,"Howl Pin","PIN_HOWL.png","https://csgostash.com/pin/32/Howl-Pin");
    /*
    IPin g2 = new Pin().populate
    IPin g3 = new Pin().populate
    IPin g4 = new Pin().populate
    IPin g5 = new Pin().populate
    IPin g6 = new Pin().populate
    */

    //PlayerModels
    // TODO: 30-08-2021 Add more PlayerModels to test data
    public static IPlayerModel h1 = new PlayerModel().populate(1,3.3,0.0,"B Squadron Officer | SAS","PLAYER_SAS.png","https://csgostash.com/agent/17/B-Squadron-Officer-SAS");
    /*
    IPlayerModel h2 = new PlayerModel().populate
    IPlayerModel h3 = new PlayerModel().populate
    IPlayerModel h4 = new PlayerModel().populate
    IPlayerModel h5 = new PlayerModel().populate
    IPlayerModel h6 = new PlayerModel().populate
    */

    //Skins
    // TODO: 30-08-2021 Add more Skins to test data
    public static ISkin i1 = new Skin().populate(1,100.0,0.0,"MP9 | Food Chain","weapon_mp9_cu_mp9_food_chain_light_large.5895f4cc864387a61a3783470037e6ed1953f7df.png","https://csgostash.com/skin/1373/MP9-Food-Chain",1.0,false,false);
    public static ISkin i2 = new Skin().populate(2,50.0,0.0,"Galil AR | Chromatic Aberration","weapon_galilar_cu_galil_chroma_pink_light_large.e29cb838de1fc6318d36ed2d7263b7fcef10a4d8.png","https://csgostash.com/skin/1375/Galil-AR-Chromatic-Aberration",0.0,true,false);
    public static ISkin i3 = new Skin().populate(3,150.0,0.0,"XM1014 | XOXO","weapon_xm1014_aq_xm1014_punk_light_large.8c6eb5bcf234179713a52b1a5e22c64c21c52007.png","https://csgostash.com/skin/1374/XM1014-XOXO",0.005,false,true);
    /*
    ISkin i4 = new Skin().populate
    ISkin i5 = new Skin().populate
    ISkin i6 = new Skin().populate
    */

    //Souvenirs
    // TODO: 30-08-2021 Add more Souvenirs to test data
    public static ISouvenirCase j1 = new SouvenirCase().populate(1,9.22,0.0,"S TEST","2019BER_NUK.png","https://csgostash.com/item/14265/Berlin-2019-Vertigo-Souvenir-Package");
    /*
    ISouvenirCase j2 = new SouvenirCase().populate
    ISouvenirCase j3 = new SouvenirCase().populate
    ISouvenirCase j4 = new SouvenirCase().populate
    ISouvenirCase j5 = new SouvenirCase().populate
    ISouvenirCase j6 = new SouvenirCase().populate
    */

    //Stickers
    // TODO: 30-08-2021 Add more Stickers to test data
    public static ISticker k1 = new Sticker().populate(1,99.99,0.0,"Sticker | Natus Vincere (Gold) | 2020 RMR","NAVI GOLD.png","https://csgostash.com/sticker/3865/Natus-Vincere-Gold-2020-RMR");
    public static ISticker k2 = new Sticker().populate(2,66.66,0.0,"Sticker | TYLOO (Gold) | 2020 RMR","TYLOO GOLD.png","https://csgostash.com/sticker/3836/TYLOO-Gold-2020-RMR");
    /*
    ISticker k3 = new Sticker().populate
    ISticker k4 = new Sticker().populate
    ISticker k5 = new Sticker().populate
    ISticker k6 = new Sticker().populate
    */

    //Tickets
    // TODO: 30-08-2021 Add more Tickets to test data
    public static ITicket l1 = new Ticket().populate(1,7.00,0.0,"Berlin 2019 Viewer Pass","BERLIN TICKET.png","https://csgostash.com/item/13929/Berlin-2019-Viewer-Pass");
    /*
    ITicket l2 = new Ticket().populate
    ITicket l3 = new Ticket().populate
    ITicket l4 = new Ticket().populate
    ITicket l5 = new Ticket().populate
    ITicket l6 = new Ticket().populate
    */

    //Vaults
    // TODO: 30-08-2021 Add more Vaults to test data
    public static IVault m1 = new Vault().populate(1,"Berlin");
    static {
        m1.addItems(a1,10);         //Capsule
        m1.addItems(a2,10);         //Capsule
        m1.addItems(a3,10);         //Capsule
        m1.addItems(b1,50);         //Case
        m1.addItems(c1,5);          //Graffiti
        m1.addItems(d1,5);          //Key
        m1.addItems(e1,1);          //MusicKit
        m1.addItems(g1,1);          //Pin
        m1.addItems(h1,1);          //PlayerModel
        m1.addItems(i1,1);          //Skin
        m1.addItems(k1,1);          //Sticker
        m1.addItems(k2,1);          //Sticker
        m1.addItems(l1,1);          //Ticket
    }
    public static IVault m2 = new Vault().populate(2,"RMR");
    static {
        m2.addItems(a4,10);         //Capsule
        m2.addItems(a5,10);         //Capsule
        m2.addItems(a6,10);         //Capsule
        m2.addItems(f1,1);          //Patch
        m2.addItems(j1,100);        //Souvenir
        m2.addItems(k2,1);          //Sticker
    }
    /*
    IVault m3 = new Vault().populate
    IVault m4 = new Vault().populate
    IVault m5 = new Vault().populate
    IVault m6 = new Vault().populate
    */
}
