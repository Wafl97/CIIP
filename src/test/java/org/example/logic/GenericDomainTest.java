package org.example.logic;

import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ICase;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.util.Attributes.*;
import static org.junit.jupiter.api.Assertions.*;

class GenericDomainTest {

    Logic domain;

    IFactory factory;

    List<Identifiable> goodList;

    @BeforeEach
    void setUp(){
        System.out.println("SETUP");
        domain = Domain.getInstance();
        domain.init(false,false,false);
        factory = domain.getFactory();
    }

    @Test
    void readAllCapsules() {
        //Test Capsules
        ICapsule a1 = factory.buildCapsule().populate(1,0.22,"Berlin 2019 Legends","2019BER_LEG.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
        ICapsule a2 = factory.buildCapsule().populate(2,0.22,"Berlin 2019 Minor","2019BER_MIN.png","https://csgostash.com/stickers/capsule/280/Berlin-2019-Minor-Challengers-Holo-Foil");
        ICapsule a3 = factory.buildCapsule().populate(3,0.22,"Berlin 2019 Returning","2019BER_RET.png","https://csgostash.com/stickers/capsule/279/Berlin-2019-Returning-Challengers-Holo-Foil");
        ICapsule a4 = factory.buildCapsule().populate(4,0.2,"RMR 2020 Challengers","2020RMR_CHA.png","https://csgostash.com/stickers/capsule/312/2020-RMR-Challengers");
        ICapsule a5 = factory.buildCapsule().populate(5,0.2,"RMR 2020 Contenders","2020RMR_CON.png","https://csgostash.com/stickers/capsule/313/2020-RMR-Contenders");
        ICapsule a6 = factory.buildCapsule().populate(6,0.2,"RMR 2020 Legends","2020RMR_LEG.png","https://csgostash.com/stickers/capsule/311/2020-RMR-Legends");
        goodList = new ArrayList<>(Arrays.asList(a1,a2,a3,a4,a5,a6));
        List<Identifiable> aList = domain.getCapsuleDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), aList.get(i).toString()));
    }

    @Test
    void readAllCases() {
        //Test Cases
        ICase b1 = factory.buildCase().populate(1,156.22,"Operation Hydra Case","CASE_HYDRA.png","https://csgostash.com/case/208/Operation-Hydra-Case");
        // TODO: 16-08-2021 Add more Cases to test data
        goodList = new ArrayList<>(Arrays.asList(b1));
        List<Identifiable> bList = domain.getCaseDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createCapsule() {
        ICapsule a7 = factory.buildCapsule().populate(99,99,"new capsule","img.png","https://csgostash.com/stickers/capsule/314/Poorly-Drawn-Capsule");
        assertTrue(domain.getCapsuleDomain().create(a7));
    }

    @Test
    void readCapsule() {
        ICapsule a7 = factory.buildCapsule().populate(99,99,"new capsule","img.png","https://csgostash.com/stickers/capsule/314/Poorly-Drawn-Capsule");
        assertEquals(a7.toString(),domain.getCapsuleDomain().read(99).toString());
    }

    @Test
    void updateCapsule() {
        ICapsule a8 = factory.buildCapsule().populate(99,99,"updated capsule","img.png","https://csgostash.com/stickers/capsule/314/Poorly-Drawn-Capsule");
        assertTrue(domain.getCapsuleDomain().update(a8));
    }

    @Test
    void deleteCapsule() {
        assertTrue(domain.getCapsuleDomain().delete(99));
    }
}