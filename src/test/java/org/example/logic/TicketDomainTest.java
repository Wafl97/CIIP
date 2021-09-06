package org.example.logic;

import org.example.logic.dto.interfaces.ITicket;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.l1;
import static org.example.util.Attributes.TICKET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketDomainTest extends GenericDomainTest{

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
}
