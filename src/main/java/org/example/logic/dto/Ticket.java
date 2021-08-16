package org.example.logic.dto;

import org.example.logic.Domain;
import org.example.logic.dto.interfaces.ICapsule;
import org.example.logic.dto.interfaces.ITicket;
import org.example.logic.dto.interfaces.comps.Identifiable;
import org.example.logic.interfaces.IGenericDomain;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Ticket extends GenericItem<ITicket> implements ITicket {

    private static final IGenericDomain TICKET_DOMAIN = Domain.getInstance().getTicketDomain();

    public Ticket() {
        super(TICKET);
    }

    @Override
    public ITicket convert2Obj(JSONObject jsonObject) {
        return (ITicket) convertHelper(jsonObject);
    }

    @Override
    public long findMaxID() {
        long maxValue = 0;
        for (Identifiable ticket : TICKET_DOMAIN.readAll()){
            if (ticket.getId() > maxValue) maxValue = ticket.getId();
        }
        return maxValue;
    }

    @Override
    public ITicket populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ITicket) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
