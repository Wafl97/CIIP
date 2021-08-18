package org.example.logic.dto;

import org.example.logic.dto.interfaces.ITicket;
import org.json.simple.JSONObject;

import static org.example.util.Attributes.*;

public final class Ticket extends GenericItem<ITicket> implements ITicket {

    public Ticket() {
        super(TICKET);
    }

    @Override
    public ITicket convert2Obj(JSONObject jsonObject) {
        return (ITicket) convertHelper(jsonObject);
    }

    @Override
    public ITicket populate(long id, double initPrice, double currPrice, String name, String image, String stashLink) {
        return (ITicket) popHelper(id,initPrice,currPrice,name,image,stashLink);
    }
}
