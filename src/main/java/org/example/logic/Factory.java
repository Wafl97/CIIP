package org.example.logic;

import org.example.logic.dto.*;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.logic.interfaces.*;
import org.example.util.Attributes;

public final class Factory implements IFactory {

    private static Factory instance;

    private Factory(){

    }

    public static Factory getInstance(){
        return instance == null ? instance = new Factory() : instance;
    }


    @Override
    public Transferable makeNew(Attributes TYPE) {
        switch (TYPE){
            case CAPSULE:
                return new Capsule();
            case CASE:
                return new Case();
            case GRAFFITI:
                return new Graffiti();
            case KEY:
                return new Key();
            case MUSICKIT:
                return new MusicKit();
            case PATCH:
                return new Patch();
            case PIN:
                return new Pin();
            case PLAYERMODEL:
                return new PlayerModel();
            case SKIN:
                return new Skin();
            case SOUVENIR:
                return new SouvenirCase();
            case STICKER:
                return new Sticker();
            case TICKET:
                return new Ticket();
            case VAULT:
                return new Vault();
            default:
                return null;
        }
    }
}
