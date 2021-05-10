package org.example.data;

import junit.framework.TestCase;
import org.example.logic.Interfaces.Container;
import org.example.logic.Interfaces.Factory;
import org.example.logic.StructureCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConnectionTest extends TestCase {

    DataConnection connection = JsonConnection.getInstance();
    Factory creator = StructureCreator.getInstance();
    DataReset reSetter = DataReset.getInstance();

    public void setUp() throws Exception {
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
        System.out.println(connection.readAllContainers());
        Container c = creator.emptyCapsule().populate(10,3.14,"NAME","OBAMA.png");
        connection.createContainer(c);
        System.out.println(connection.readAllContainers());
    }

    @Test
    public void testCreateContainer() {
        Container c = creator.emptyCapsule().populate(10,3.14,"NAME","OBAMA.png");
        connection.createContainer(c);
    }

    public void testReadContainer() {
    }

    public void testUpdateContainer() {
    }

    public void testDeleteContainer() {
    }
}