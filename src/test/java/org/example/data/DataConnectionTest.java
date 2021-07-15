package org.example.data;

import junit.framework.TestCase;
import org.example.data.interfaces.DataConnection;
import org.example.logic.interfaces.Factory;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.ISticker;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DataConnectionTest extends TestCase {

    DataConnection connection = JsonConnection.getInstance();
    Factory creator = StructureCreator.getInstance();
    DataReset reSetter = DataReset.getInstance();

    //Dummy objs
    ISticker st1 = creator.emptySticker().populate(1,99.99,"name","260fx260f.png","link");
    ISticker st2 = creator.emptySticker().populate(2,66.66,"namesdf","260fx260f.png","linksdf");
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
        assertEquals(expectedList, connection.readAllStickers());
    }

    @Test
    public void testReadSticker(){
        assertEquals(st1.convert2JSON(),connection.readSticker(1));
    }

    @Test
    public void testCreateSticker(){
        ISticker sticker = creator.emptySticker();
        sticker.populate(100,99.99,"Test","Image","Link.com");
        connection.createSticker(sticker.convert2JSON());

        assertEquals(sticker.convert2JSON(),connection.readSticker(100));
    }

    @Test
    public void testUpdateSticker(){
        ISticker sticker = creator.emptySticker().convert2Obj(connection.readSticker(2));
        sticker.setName("new name");
        connection.updateSticker(sticker.convert2JSON());
        assertEquals(sticker.convert2JSON(),connection.readSticker(2));
    }

    @Test
    public void testDeleteSticker(){
        connection.deleteSticker(1);
        assertNotSame(expectedList,connection.readAllStickers());
    }
}