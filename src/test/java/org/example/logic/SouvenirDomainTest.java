package org.example.logic;

import org.example.logic.dto.interfaces.ISouvenirCase;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.j1;
import static org.example.util.Attributes.SOUVENIR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SouvenirDomainTest extends GenericDomainTest{

    @Test
    void readAllSouvenirs() {
        goodList = new ArrayList<>(Arrays.asList(j1));
        List<Transferable<ISouvenirCase>> jList = domain.getSouvenirCaseDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), jList.get(i).toString()));
    }

    @Test
    void createSouvenir() {
        ISouvenirCase j7 = ((ISouvenirCase) factory.makeNew(SOUVENIR)).populate(-1,99.99,0.0,"new souvenir case","img.png","https://csgostash.com/item/14265/Berlin-2019-Vertigo-Souvenir-Package");
        assertTrue(domain.getSouvenirCaseDomain().create(j7));
    }

    @Test
    void readSouvenir() {
        assertEquals(j1.toString(),domain.getSouvenirCaseDomain().read(1).toString());
    }

    @Test
    void updateSouvenir() {
        j1.setName("S TEST++");
        assertTrue(domain.getSouvenirCaseDomain().update(j1));
        j1.setName("S TEST");
    }

    @Test
    void deleteSouvenir() {
        assertTrue(domain.getSouvenirCaseDomain().delete(j1.findMaxID()));
    }
}
