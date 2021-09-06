package org.example.logic;

import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.*;
import static org.example.logic.TestData.a1;
import static org.example.util.Attributes.CAPSULE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CapsuleDomainTest extends GenericDomainTest{

    @Test
    void readAllCapsules() {
        goodList = new ArrayList<>(Arrays.asList(a1,a2,a3,a4,a5,a6));
        List<Transferable<ICapsule>> aList = domain.getCapsuleDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), aList.get(i).toString()));
    }

    @Test
    void createCapsule() {
        ICapsule a7 = ((ICapsule) factory.makeNew(CAPSULE)).populate(-1,99.99,0.0,"new capsule","img.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
        assertTrue(domain.getCapsuleDomain().create(a7));
    }

    @Test
    void readCapsule() {
        assertEquals(a1.toString(),domain.getCapsuleDomain().read(1).toString());
    }

    @Test
    void updateCapsule() {
        a1.setName("Berlin 2019 Legends++");
        assertTrue(domain.getCapsuleDomain().update(a1));
        a1.setName("Berlin 2019 Legends");
    }

    @Test
    void deleteCapsule() {
        assertTrue(domain.getCapsuleDomain().delete(a1.findMaxID()));
    }
}
