package org.example.logic;

import org.example.logic.dto.interfaces.IMusicKit;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.e1;
import static org.example.util.Attributes.MUSICKIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MusicKitDomainTest extends GenericDomainTest{

    @Test
    void readAllMusicKits() {
        goodList = new ArrayList<>(Arrays.asList(e1));
        List<Transferable<IMusicKit>> eList = domain.getMusicKitDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), eList.get(i).toString()));
    }

    @Test
    void createMusicKit() {
        IMusicKit e7 = ((IMusicKit) factory.makeNew(MUSICKIT)).populate(-1,99.99,0.0,"new music kit","img.png","https://csgostash.com/music/2/Crimson-Assault-Daniel-Sadowski");
        assertTrue(domain.getMusicKitDomain().create(e7));
    }

    @Test
    void readMusicKit() {
        assertEquals(e1.toString(),domain.getMusicKitDomain().read(1).toString());
    }

    @Test
    void updateMusicKit() {
        e1.setName("Crimson Assault++");
        assertTrue(domain.getMusicKitDomain().update(e1));
        e1.setName("Crimson Assault");
    }

    @Test
    void deleteMusicKit() {
        assertTrue(domain.getMusicKitDomain().delete(e1.findMaxID()));
    }
}
