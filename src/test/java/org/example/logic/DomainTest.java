package org.example.logic;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DomainTest extends GenericDomainTest{

    @Test
    void domainTest(){
        assertEquals("CIIP",domain.getAppName());
        assertNotEquals("UNKNOWN",domain.getVersion());
    }

    @Test
    void fileHandlerLoadTest(){
        File file = domain.getFileHandler().load("deag.png");
        assertEquals("deag.png",file.getName());
    }
}
