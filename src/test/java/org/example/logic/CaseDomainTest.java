package org.example.logic;

import org.example.logic.dto.interfaces.ICase;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.b1;
import static org.example.util.Attributes.CASE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaseDomainTest extends GenericDomainTest{

    @Test
    void readAllCases() {
        goodList = new ArrayList<>(Arrays.asList(b1/*,b2,b3,b4,b5,b6*/));
        List<Transferable<ICase>> bList = domain.getCaseDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createCase() {
        ICase b7 = ((ICase) factory.makeNew(CASE)).populate(-1,99.99,0.0,"new case","img.png","https://csgostash.com/case/208/Operation-Hydra-Case");
        assertTrue(domain.getCaseDomain().create(b7));
    }

    @Test
    void readCase() {
        assertEquals(b1.toString(),domain.getCaseDomain().read(1).toString());
    }

    @Test
    void updateCase() {
        b1.setName("Operation Hydra Case++");
        assertTrue(domain.getCaseDomain().update(b1));
        b1.setName("Operation Hydra Case");
    }

    @Test
    void deleteCase() {
        assertTrue(domain.getCaseDomain().delete(b1.findMaxID()));
    }
}
