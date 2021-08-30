package org.example.logic;

import org.example.data.DataReset;
import org.example.logic.dto.*;
import org.example.logic.dto.interfaces.*;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.util.Attributes.*;
import static org.example.logic.TestData.*;

class GenericDomainTest {

    Logic domain = Domain.getInstance();

    IFactory factory;

    DataReset reset = DataReset.getInstance();

    List<Identifiable> goodList;

    @BeforeEach
    void setUp(){
        domain.init(false,false,true);
        factory = domain.getFactory();

        //Resting data
        reset.reset();
    }



    @Test
    void fileHandlerLoadTest(){
        File file = domain.getFileHandler().load("deag.png");
        assertEquals("deag.png",file.getName());
    }

    @Test
    void readAllCapsules() {
        goodList = new ArrayList<>(Arrays.asList(a1,a2,a3,a4,a5,a6));
        List<Transferable<ICapsule>> aList = domain.getCapsuleDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), aList.get(i).toString()));
    }

    @Test
    void createCapsule() {
        ICapsule a7 = ((ICapsule) factory.makeNew(CAPSULE)).populate(-1,99.99,0.0,"new capsule","img.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
        assertTrue(domain.getCapsuleDomain().create(a7));
    }

    @Test
    void readCapsule() {
        System.out.println(a1.toString());
        System.out.println(domain.getCapsuleDomain().read(1).toString());
        assertEquals(a1.toString(),domain.getCapsuleDomain().read(1).toString());
    }

    @Test
    void updateCapsule() {
        a1.setName("Berlin 2019 Legends++");
        assertTrue(domain.getCapsuleDomain().update(a1));
        a1.setName("Berlin 2019 Legends");
    }

    @Test
    void deleteCapsule() {
        assertTrue(domain.getCapsuleDomain().delete(a1.findMaxID()));
    }

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
        i1.setName("TEST++");
        assertTrue(domain.getSkinDomain().update(i1));
        i1.setName("TEST");
    }

    @Test
    void deleteSkin() {
        assertTrue(domain.getSkinDomain().delete(i1.findMaxID()));
    }

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

    @Test
    void readAllTickets() {
        goodList = new ArrayList<>(Arrays.asList(l1));
        List<Transferable<ITicket>> lList = domain.getTicketDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), lList.get(i).toString()));
    }

    @Test
    void createTicket() {
        ITicket l7 = ((ITicket) factory.makeNew(TICKET)).populate(-1,99.99,0.0,"new ticket","img.png","https://csgostash.com/item/13929/Berlin-2019-Viewer-Pass");
        assertTrue(domain.getTicketDomain().create(l7));
    }

    @Test
    void readTicket() {
        assertEquals(l1.toString(),domain.getTicketDomain().read(1).toString());
    }

    @Test
    void updateTicket() {
        l1.setName("Berlin 2019 Viewer Pass++");
        assertTrue(domain.getTicketDomain().update(l1));
        l1.setName("Berlin 2019 Viewer Pass");
    }

    @Test
    void deleteTicket() {
        assertTrue(domain.getTicketDomain().delete(l1.findMaxID()));
    }

    @Test
    void readAllVaults() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createVault() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readVault() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateVault() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteVault() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }
}