package org.example.logic;

import org.example.logic.dto.interfaces.IPlayerModel;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.h1;
import static org.example.util.Attributes.PLAYERMODEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerModelDomainTest extends GenericDomainTest{

    @Test
    void readAllPlayerModels() {
        goodList = new ArrayList<>(Arrays.asList(h1));
        List<Transferable<IPlayerModel>> hList = domain.getPlayerModelDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), hList.get(i).toString()));
    }

    @Test
    void createPlayerModel() {
        IPlayerModel h7 = ((IPlayerModel) factory.makeNew(PLAYERMODEL)).populate(-1,99.99,0.0,"new player model","img.png","https://csgostash.com/agent/17/B-Squadron-Officer-SAS");
        assertTrue(domain.getPlayerModelDomain().create(h7));
    }

    @Test
    void readPlayerModel() {
        assertEquals(h1.toString(),domain.getPlayerModelDomain().read(1).toString());
    }

    @Test
    void updatePlayerModel() {
        h1.setName("B Squadron Officer | SAS++");
        assertTrue(domain.getPlayerModelDomain().update(h1));
        h1.setName("B Squadron Officer | SAS");
    }

    @Test
    void deletePlayerModel() {
        assertTrue(domain.getPlayerModelDomain().delete(h1.findMaxID()));
    }
}
