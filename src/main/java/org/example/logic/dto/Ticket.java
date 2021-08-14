package org.example.logic.dto;

import org.example.logic.Domain;
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
        JSONObject innerObj = (JSONObject) jsonObject.get(jsonAttribute);
        return  populate(
                (long)      innerObj.get(ID.toString()),
                (double)    innerObj.get(INIT_PRICE.toString()),
                (String)    innerObj.get(NAME.toString()),
                (String)    innerObj.get(IMAGE.toString()),
                (String)    innerObj.get(STASH_LINK.toString())
        );
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
    public ITicket populate(long id, double initPrice, String name, String image, String stashLink) {
        setId(id);
        setInitPrice(initPrice);
        setName(name);
        setImage(image);
        setStashLink(stashLink);
        return this;
    }
}
