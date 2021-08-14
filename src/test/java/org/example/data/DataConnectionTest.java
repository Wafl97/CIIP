package org.example.data;

import junit.framework.TestCase;
import org.example.data.interfaces.DataConnection;
import org.example.logic.dto.Sticker;
import org.example.logic.interfaces.IFactory;
import org.example.logic.Factory;
import org.example.logic.interfaces.dto.ISticker;
import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.util.Attributes;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.util.Attributes.*;

public class DataConnectionTest extends TestCase {

    DataConnection connection = JsonConnection.getInstance();
    IFactory creator = Factory.getInstance();
    DataReset reSetter = DataReset.getInstance();

    //Dummy objs
    ISticker st1 = new Sticker().populate(1,99.99,"name","260fx260f.png","link");
    ISticker st2 = new Sticker().populate(2,66.66,"namesdf","260fx260f.png","linksdf");
    List<JSONObject> expectedList = Arrays.asList(st1.convert2JSON(),st2.convert2JSON());

    public void setUp() throws Exception {
        reSetter.reset();
        connection.connect(false);
    }

    public void tearDown() throws Exception {
    }

    @BeforeEach
    public void beforeEach() {
    }

    public void testReadAllInvestments() {
    }

    public void testCreateInvestment() {
    }

    public void testReadInvestment() {
    }

    public void testUpdateInvestment() {
    }

    public void testDeleteInvestment() {
    }

    @Test
    public void testReadAllContainers() {

    }

    @Test
    public void testCreateContainer() {

    }

    @Test
    public void testReadContainer() {
    }

    @Test
    public void testUpdateContainer() {
    }

    @Test
    public void testDeleteContainer() {
    }

    @Test
    public void testReadAllStickers(){
        assertEquals(expectedList, connection.getStickerConnection().readAll());
    }

    @Test
    public void testReadSticker(){
        assertEquals(st1.convert2JSON(),connection.getStickerConnection().read(1));
    }

    @Test
    public void testCreateSticker(){
        ISticker sticker = (ISticker) creator.makeNew(STICKER);
        sticker.populate(100,99.99,"Test","Image","Link.com");
        connection.getStickerConnection().create(sticker.convert2JSON());

        assertEquals(sticker.convert2JSON(),connection.getStickerConnection().read(100));
    }

    @Test
    public void testUpdateSticker(){
        ISticker sticker = (ISticker) creator.makeNew(STICKER).convert2Obj(connection.getStickerConnection().read(2));
        sticker.setName("new name");
        connection.getStickerConnection().update(sticker.convert2JSON());
        assertEquals(sticker.convert2JSON(),connection.getStickerConnection().read(2));
    }

    @Test
    public void testDeleteSticker(){
        connection.getStickerConnection().delete(1);
        assertNotSame(expectedList,connection.getStickerConnection().readAll());
    }
}