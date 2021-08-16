package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.junit.jupiter.api.AfterEach;
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
        domain.init(false,false,false);
        factory = domain.getFactory();
    }


    @Test
    void currPrice() {
        ICapsule a1 = factory.buildCapsule().populate(1,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
        a1.updateCurrPrice();
        a1.getCurrPrice();
        double d = a1.getCurrPrice() - a1.getInitPrice();
        assertEquals(d,a1.getDiffPrice());
    }

    @Test
    void setStashLink() {
        //Bad link test
        ICapsule a8 = factory.buildCapsule();
        assertThrows(IllegalStateException.class, () -> a8.populate(1001,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","BAD.LINK"));
    }

    @Test
    void convert2JSON() {
    }
}