package org.example.logic;

import org.example.logic.dto.interfaces.IKey;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.d1;
import static org.example.util.Attributes.KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyDomainTest extends GenericDomainTest{

    @Test
    void readAllKeys() {
        goodList = new ArrayList<>(Arrays.asList(d1/*,d2,d3,d4,d5,d6*/));
        List<Transferable<IKey>> dList = domain.getKeyDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), dList.get(i).toString()));
    }

    @Test
    void createKey() {
        IKey d7 = ((IKey) factory.makeNew(KEY)).populate(-1,99.99,0.0,"new key","img.png","https://csgostash.com/item/9791/Operation-Hydra-Case-Key");
        assertTrue(domain.getKeyDomain().create(d7));
    }

    @Test
    void readKey() {
        assertEquals(d1.toString(),domain.getKeyDomain().read(1).toString());
    }

    @Test
    void updateKey() {
        d1.setName("Operation Hydra Case Key++");
        assertTrue(domain.getKeyDomain().update(d1));
        d1.setName("Operation Hydra Case Key");
    }

    @Test
    void deleteKey() {
        assertTrue(domain.getKeyDomain().delete(d1.findMaxID()));
    }
}
