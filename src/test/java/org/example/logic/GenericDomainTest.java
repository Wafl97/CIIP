package org.example.logic;

import org.example.data.DataReset;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IFactory;
import org.example.logic.interfaces.Logic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}