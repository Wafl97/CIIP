package org.example.logic;

import org.example.logic.dto.interfaces.ISkin;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.*;
import static org.example.logic.TestData.i1;
import static org.example.util.Attributes.SKIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkinDomainTest extends GenericDomainTest{

    @Test
    void readAllSkins() {
        goodList = new ArrayList<>(Arrays.asList(i1,i2,i3));
        List<Transferable<ISkin>> iList = domain.getSkinDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), iList.get(i).toString()));
    }

    @Test
    void createSkin() {
        ISkin i7 = ((ISkin) factory.makeNew(SKIN)).populate(-1,99.99,0.0,"new skin","img.png","https://csgostash.com/skin/1373/MP9-Food-Chain",0.03,false,false);
        assertTrue(domain.getSkinDomain().create(i7));
    }

    @Test
    void readSkin() {
        assertEquals(i1.toString(),domain.getSkinDomain().read(1).toString());
    }

    @Test
    void updateSkin() {
        i1.setName("MP9 | Food Chain++");
        assertTrue(domain.getSkinDomain().update(i1));
        i1.setName("MP9 | Food Chain");
    }

    @Test
    void deleteSkin() {
        assertTrue(domain.getSkinDomain().delete(i1.findMaxID()));
    }
}
