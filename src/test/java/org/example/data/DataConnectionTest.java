package org.example.data;

import junit.framework.TestCase;
import org.example.data.interfaces.DataConnection;
import org.example.logic.interfaces.Factory;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.ICapsule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConnectionTest extends TestCase {

    DataConnection connection = JsonConnection.getInstance();
    Factory creator = StructureCreator.getInstance();
    DataReset reSetter = DataReset.getInstance();

    public void setUp() throws Exception {
        ;
        connection.connect();
    }

    public void tearDown() throws Exception {
    }

    @BeforeEach
    public void beforeEach() {
        reSetter.reset();
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
        System.out.println(connection.readAllCapsules());
        ICapsule c = creator.emptyCapsule().populate(10,3.14,"NAME","OBAMA.png","LINK.dk");
        connection.createCapsule(c.convert2JSON());
        System.out.println(connection.readAllCapsules());
    }

    @Test
    public void testCreateContainer() {
        ICapsule c = creator.emptyCapsule().populate(10,3.14,"NAME","OBAMA.png","URL.com");
        connection.createCapsule(c.convert2JSON());
    }

    public void testReadContainer() {
    }

    public void testUpdateContainer() {
    }

    public void testDeleteContainer() {
    }
}