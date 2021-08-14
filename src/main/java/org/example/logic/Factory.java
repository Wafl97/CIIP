package org.example.logic;

import org.example.logic.dto.*;
import org.example.logic.dto.interfaces.*;
import org.example.logic.interfaces.*;
import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.util.Attributes;

public final class Factory implements IFactory {

    private static Factory instance;

    private Factory(){

    }

    public static Factory getInstance(){
        return instance == null ? instance = new Factory() : instance;
    }

    @Override
    public ICapsule buildCapsule() {
        return new Capsule();
    }

    @Override
    public ICase buildCase() {
        return new Case();
    }

    @Override
    public IGraffiti buildGraffiti() {
        return new Graffiti();
    }

    @Override
    public IKey buildKey() {
        return new Key();
    }

    @Override
    public IMusicKit buildMusicKit() {
        return new MusicKit();
    }

    @Override
    public IPatch buildPatch() {
        return new Patch();
    }

    @Override
    public IPin buildPin() {
        return new Pin();
    }

    @Override
    public IPlayerModel buildPlayerModel() {
        return new PlayerModel();
    }

    @Override
    public ISkin buildSkin() {
        return new Skin();
    }

    @Override
    public ISouvenirCase buildSouvenir() {
        return new SouvenirCase();
    }

    @Override
    public ISticker buildSticker() {
        return new Sticker();
    }

    @Override
    public ITicket buildTicket() {
        return new Ticket();
    }

    @Override
    public IVault buildVault() {
        return new Vault();
    }

    @Override
    public Convertible makeNew(Attributes TYPE) {
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
