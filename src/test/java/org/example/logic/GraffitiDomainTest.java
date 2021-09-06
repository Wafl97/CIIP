package org.example.logic;

import org.example.logic.dto.interfaces.IGraffiti;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.c1;
import static org.example.util.Attributes.GRAFFITI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraffitiDomainTest extends GenericDomainTest{

    @Test
    void readAllGraffities() {
        goodList = new ArrayList<>(Arrays.asList(c1));
        List<Transferable<IGraffiti>> bList = domain.getGraffitiDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createGraffiti() {
        IGraffiti c7 = ((IGraffiti) factory.makeNew(GRAFFITI)).populate(-1,99.99,0.0,"new graffiti","img.png","https://csgostash.com/graffiti/15/Rising-Skull");
        assertTrue(domain.getGraffitiDomain().create(c7));
    }

    @Test
    void readGraffiti() {
        assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateGraffiti() {
        c1.setName("Rising Skull++");
        assertTrue(domain.getGraffitiDomain().update(c1));
        c1.setName("Rising Skull");
    }

    @Test
    void deleteGraffiti() {
        assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }
}
