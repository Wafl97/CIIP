package org.example.logic;

import org.example.logic.dto.interfaces.Item;
import org.example.util.Attributes;

public final class GenericItemDomain<T> extends GenericDomain<Item<T>> {

    public GenericItemDomain(Attributes TYPE){
        super(TYPE);
        switch (TYPE){
            case CAPSULE:
                SUB_CON = DATA_FACADE.getDataConnection().getCapsuleConnection();
                break;
            case CASE:
                SUB_CON = DATA_FACADE.getDataConnection().getCaseConnection();
                break;
            case GRAFFITI:
                SUB_CON = DATA_FACADE.getDataConnection().getGraffitiConnection();
                break;
            case KEY:
                SUB_CON = DATA_FACADE.getDataConnection().getKeyConnection();
                break;
            case MUSICKIT:
                SUB_CON = DATA_FACADE.getDataConnection().getMusicKitConnection();
                break;
            case PATCH:
                SUB_CON = DATA_FACADE.getDataConnection().getPatchConnection();
                break;
            case PIN:
                SUB_CON = DATA_FACADE.getDataConnection().getPinConnection();
                break;
            case PLAYERMODEL:
                SUB_CON = DATA_FACADE.getDataConnection().getPlayerModelConnection();
                break;
            case SKIN:
                SUB_CON = DATA_FACADE.getDataConnection().getSkinConnection();
                break;
            case SOUVENIR:
                SUB_CON = DATA_FACADE.getDataConnection().getSouvenirConnection();
                break;
            case STICKER:
                SUB_CON = DATA_FACADE.getDataConnection().getStickerConnection();
                break;
            case TICKET:
                SUB_CON = DATA_FACADE.getDataConnection().getTicketConnection();
                break;
            case VAULT:
                SUB_CON = DATA_FACADE.getDataConnection().getVaultConnection();
                break;
            default:
                SUB_CON = null;
                break;
        }
    }
}
