package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ISkin;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.example.util.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericItemTest {


    Logic domain;

    IFactory factory;

    @BeforeEach
    void setUp() {
        System.out.println("SETUP");
        domain = Domain.getInstance();
        domain.init(false,true,true);
        factory = domain.getFactory();
    }


    @Test
    void capsuleCurrPrice() {
        ICapsule capsule = ((ICapsule) factory.makeNew(Attributes.CAPSULE)).populate(1,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");

        capsule.updateCurrPrice();
        double d = capsule.getCurrPrice() - capsule.getInitPrice();
        assertEquals(d,capsule.getDiffPrice());
    }

    @Test
    void skinCurrPrice(){
        ISkin skin = ((ISkin) factory.makeNew(Attributes.SKIN)).populate(1,100.0,0.0,"MP9 | Food Chain","weapon_galilar_cu_galil_chroma_pink_light_large.e29cb838de1fc6318d36ed2d7263b7fcef10a4d8.png","https://csgostash.com/skin/1375/Galil-AR-Chromatic-Aberration",1.0,false,false);

        skin.updateCurrPrice();
        double d = skin.getCurrPrice() - skin.getInitPrice();
        assertEquals(d,skin.getDiffPrice());
    }

    @Test
    void setStashLink() {
        //Bad link test
        ICapsule a8 = ((ICapsule) factory.makeNew(Attributes.CAPSULE)).populate(1001,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","BAD.LINK");
        assertThrows(IllegalStateException.class, a8::updateCurrPrice);
    }

    @Test
    void convert2JSON() {
    }
}