package org.example.logic.sub;

import org.example.data.DataFacade;
import org.example.data.interfaces.IDataFacade;
import org.example.logic.StructureCreator;
import org.example.logic.interfaces.Factory;
import org.example.logic.interfaces.dto.ITicket;
import org.example.logic.interfaces.sub.ITicketDomain;
import org.example.util.ConsoleColors;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TicketDomain implements ITicketDomain {

    private static TicketDomain instance;

    private TicketDomain(){

    }

    public static TicketDomain getInstance(){
        return instance == null ? instance = new TicketDomain() : instance;
    }

    private static final IDataFacade DATA_FACADE = DataFacade.getInstance();
    private static final Factory CREATOR = StructureCreator.getInstance();

    private List<ITicket> ticketCache;

    @Override
    public List<ITicket> readAllTickets() {
        if (ticketCache == null){
            System.out.println("===========================================\n" + ConsoleColors.PURPLE + "Caching Tickets" + ConsoleColors.RESET);
            ticketCache = new ArrayList<>();
            for (JSONObject o : DATA_FACADE.getDataConnection().readAllTickets()){
                ITicket newTicket = CREATOR.emptyTicket().convert2Obj(o);
                ticketCache.add(newTicket);
                System.out.println("Ticket [" + ConsoleColors.BLUE + newTicket.getName() + ConsoleColors.RESET + "]" + ConsoleColors.GREEN + " Cached" + ConsoleColors.RESET);
            }
            System.out.println("Cache Size [" + ConsoleColors.CYAN + ticketCache.size() + ConsoleColors.RESET + "]\n===========================================\n");
        }
        return ticketCache;
    }

    @Override
    public void createTicket(ITicket ticket) {
        if (ticketCache == null) readAllTickets();
        DATA_FACADE.getDataConnection().createTicket(ticket.convert2JSON());
        ticketCache.add(ticket);
    }

    @Override
    public ITicket readTicket(long id) {
        if (ticketCache == null) readAllTickets();
        return ticketCache.stream().filter(ticket -> ticket.getId() == id).findFirst().get();
    }

    @Override
    public void updateTicket(ITicket ticket) {
        if (ticketCache == null) readAllTickets();
        DATA_FACADE.getDataConnection().updateTicket(ticket.convert2JSON());
        ticketCache.removeIf(tck -> tck.getId() == ticket.getId());
        ticketCache.add(ticket);
    }

    @Override
    public void deleteTicket(long id) {
        if (ticketCache == null) readAllTickets();
        DATA_FACADE.getDataConnection().deleteTicket(id);
        ticketCache.removeIf(ticket -> ticket.getId() == id);
    }
}
