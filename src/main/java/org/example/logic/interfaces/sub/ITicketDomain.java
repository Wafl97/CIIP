package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ITicket;

import java.util.List;

public interface ITicketDomain {

    List<ITicket> readAllTickets();
    void createTicket(ITicket ticket);
    ITicket readTicket(long id);
    void updateTicket(ITicket ticket);
    void deleteTicket(long id);
}
