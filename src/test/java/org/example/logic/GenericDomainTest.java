package org.example.logic;

import org.example.data.DataReset;
import org.example.logic.dto.Capsule;
import org.example.logic.dto.Case;
import org.example.logic.dto.Graffiti;
import org.example.logic.dto.Key;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ICase;
import org.example.logic.dto.interfaces.IGraffiti;
import org.example.logic.dto.interfaces.IKey;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.util.Attributes.*;

class GenericDomainTest {

    Logic domain = Domain.getInstance();

    IFactory factory;

    DataReset reset = DataReset.getInstance();

    List<Identifiable> goodList;

    //Capsules
    ICapsule a1 = new Capsule().populate(1,0.22,0.0,"Berlin 2019 Legends","2019BER_LEG.png","https://csgostash.com/stickers/capsule/278/Berlin-2019-Legends-Holo-Foil");
    ICapsule a2 = new Capsule().populate(2,0.22,0.0,"Berlin 2019 Minor","2019BER_MIN.png","https://csgostash.com/stickers/capsule/280/Berlin-2019-Minor-Challengers-Holo-Foil");
    ICapsule a3 = new Capsule().populate(3,0.22,0.0,"Berlin 2019 Returning","2019BER_RET.png","https://csgostash.com/stickers/capsule/279/Berlin-2019-Returning-Challengers-Holo-Foil");
    ICapsule a4 = new Capsule().populate(4,0.2,0.0,"RMR 2020 Challengers","2020RMR_CHA.png","https://csgostash.com/stickers/capsule/312/2020-RMR-Challengers");
    ICapsule a5 = new Capsule().populate(5,0.2,0.0,"RMR 2020 Contenders","2020RMR_CON.png","https://csgostash.com/stickers/capsule/313/2020-RMR-Contenders");
    ICapsule a6 = new Capsule().populate(6,0.2,0.0,"RMR 2020 Legends","2020RMR_LEG.png","https://csgostash.com/stickers/capsule/311/2020-RMR-Legends");

    //Cases
    // TODO: 16-08-2021 Add more Cases to test data
    ICase b1 = new Case().populate(1,156.22,0.0,"Operation Hydra Case","CASE_HYDRA.png","https://csgostash.com/case/208/Operation-Hydra-Case");
    /*
    ICase b2 = new Case().populate
    ICase b3 = new Case().populate
    ICase b4 = new Case().populate
    ICase b5 = new Case().populate
    ICase b6 = new Case().populate
     */

    //Graffities
    // TODO: 16-08-2021 Add more Cases to test data
    IGraffiti c1 = new Graffiti().populate(1,1.22,0.0,"Rising Skull","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
    /*
    IGraffiti c2 = new Graffiti().populate
    IGraffiti c3 = new Graffiti().populate
    IGraffiti c4 = new Graffiti().populate
    IGraffiti c5 = new Graffiti().populate
    IGraffiti c6 = new Graffiti().populate
     */

    //Keys
    IKey d1 = new Key().populate(1,2.5,0.0,"Operation Hydra Case Key","KEY_HYDRA.png","https://csgostash.com/item/9791/Operation-Hydra-Case-Key");

    //MusicKits

    //Patches

    //Pins

    //PlayerModels

    //Skins

    //Souvenirs

    //Stickers

    //Tickets

    //Vaults

    @BeforeEach
    void setUp(){
        domain.init(false,false,false);
        factory = domain.getFactory();

        //Resting data
        reset.reset();
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
        assertEquals(a1.toString(),domain.getCapsuleDomain().read(1).toString());
    }

    @Test
    void updateCapsule() {
        a1.setName("Berlin 2019 Minor++");
        assertTrue(domain.getCapsuleDomain().update(a1));
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
    }

    @Test
    void deleteKey() {
        assertTrue(domain.getKeyDomain().delete(d1.findMaxID()));
    }

    @Test
    void readAllMusicKits() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createMusicKit() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readMusicKit() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateMusicKit() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteMusicKit() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllPatchs() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createPatch() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readPatch() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updatePatch() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deletePatch() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllPins() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createPin() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readPin() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updatePin() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deletePin() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllPlayerModels() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createPlayerModel() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readPlayerModel() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updatePlayerModel() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deletePlayerModel() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllSkins() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createSkin() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readSkin() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateSkin() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteSkin() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllSouvenirs() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createSouvenir() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readSouvenir() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateSouvenir() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteSouvenir() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllStickers() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createSticker() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readSticker() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateSticker() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteSticker() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
    }

    @Test
    void readAllTickets() {
        // TODO: 16-08-2021
        //goodList = new ArrayList<>(Arrays.asList(c1));
        //List<Identifiable> bList = domain.getGraffitiDomain().readAll();
        //IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), bList.get(i).toString()));
    }

    @Test
    void createTicket() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().create(c2));
    }

    @Test
    void readTicket() {
        // TODO: 16-08-2021
        //assertEquals(c1.toString(),domain.getGraffitiDomain().read(1).toString());
    }

    @Test
    void updateTicket() {
        // TODO: 16-08-2021
        //c1.populate(1,1.22,"Rising Skull++","GRAFFITI_SKULL.png","https://csgostash.com/graffiti/15/Rising-Skull");
        //assertTrue(domain.getGraffitiDomain().update(c1));
    }

    @Test
    void deleteTicket() {
        // TODO: 16-08-2021
        //assertTrue(domain.getGraffitiDomain().delete(c1.findMaxID()));
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