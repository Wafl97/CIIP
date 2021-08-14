package org.example.logic.interfaces;

import org.example.logic.interfaces.dto.*;
import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.util.Attributes;

public interface IFactory {

    ICapsule buildCapsule();
    ICase buildCase();
    IGraffiti buildGraffiti();
    IKey buildKey();
    IMusicKit buildMusicKit();
    IPatch buildPatch();
    IPin buildPin();
    IPlayerModel buildPlayerModel();
    ISkin buildSkin();
    ISouvenirCase buildSouvenir();
    ISticker buildSticker();
    ITicket buildTicket();
    IVault buildVault();

    Convertible makeNew(Attributes TYPE);
}
