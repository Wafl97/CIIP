package org.example.logic;

import org.example.logic.dto.interfaces.IPin;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.g1;
import static org.example.util.Attributes.PIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PinDomainTest extends GenericDomainTest{

    @Test
    void readAllPins() {
        goodList = new ArrayList<>(Arrays.asList(g1));
        List<Transferable<IPin>> gList = domain.getPinDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), gList.get(i).toString()));
    }

    @Test
    void createPin() {
        IPin g7 = ((IPin) factory.makeNew(PIN)).populate(-1,99.99,0.0,"new pin","img.png","https://csgostash.com/pin/32/Howl-Pin");
        assertTrue(domain.getPinDomain().create(g7));
    }

    @Test
    void readPin() {
        assertEquals(g1.toString(),domain.getPinDomain().read(1).toString());
    }

    @Test
    void updatePin() {
        g1.setName("Howl Pin++");
        assertTrue(domain.getPinDomain().update(g1));
        g1.setName("Howl Pin");
    }

    @Test
    void deletePin() {
        assertTrue(domain.getPinDomain().delete(g1.findMaxID()));
    }
}
