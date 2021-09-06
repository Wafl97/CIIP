package org.example.logic;

import org.example.logic.dto.interfaces.ISticker;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.k1;
import static org.example.logic.TestData.k2;
import static org.example.util.Attributes.STICKER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StickerDomainTest extends GenericDomainTest{

    @Test
    void readAllStickers() {
        goodList = new ArrayList<>(Arrays.asList(k1,k2));
        List<Transferable<ISticker>> kList = domain.getStickerDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), kList.get(i).toString()));
    }

    @Test
    void createSticker() {
        ISticker k7 = ((ISticker) factory.makeNew(STICKER)).populate(-1,99.99,0.0,"new sticker","img.png","https://csgostash.com/sticker/3836/TYLOO-Gold-2020-RMR");
        assertTrue(domain.getStickerDomain().create(k7));
    }

    @Test
    void readSticker() {
        assertEquals(k1.toString(),domain.getStickerDomain().read(1).toString());
    }

    @Test
    void updateSticker() {
        k1.setName("Sticker | Natus Vincere (Gold) | 2020 RMR++");
        assertTrue(domain.getStickerDomain().update(k1));
        k1.setName("Sticker | Natus Vincere (Gold) | 2020 RMR");
    }

    @Test
    void deleteSticker() {
        assertTrue(domain.getStickerDomain().delete(k1.findMaxID()));
    }
}
