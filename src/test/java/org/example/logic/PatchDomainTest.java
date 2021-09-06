package org.example.logic;

import org.example.logic.dto.interfaces.IPatch;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.f1;
import static org.example.util.Attributes.PATCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatchDomainTest extends GenericDomainTest{

    @Test
    void readAllPatches() {
        goodList = new ArrayList<>(Arrays.asList(f1));
        List<Transferable<IPatch>> fList = domain.getPatchDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), fList.get(i).toString()));
    }

    @Test
    void createPatch() {
        IPatch f7 = ((IPatch) factory.makeNew(PATCH)).populate(-1,99.99,0.0,"new patch","img.png","https://csgostash.com/patch/9/Crazy-Banana");
        assertTrue(domain.getPatchDomain().create(f7));
    }

    @Test
    void readPatch() {
        assertEquals(f1.toString(),domain.getPatchDomain().read(1).toString());
    }

    @Test
    void updatePatch() {
        f1.setName("Patch | Crazy Banana++");
        assertTrue(domain.getPatchDomain().update(f1));
        f1.setName("Patch | Crazy Banana");
    }

    @Test
    void deletePatch() {
        assertTrue(domain.getPatchDomain().delete(f1.findMaxID()));
    }
}
