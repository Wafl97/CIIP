package org.example.logic.interfaces;

import org.example.logic.interfaces.dto.*;

public interface Factory {

    ICapsule emptyCapsule();
    ISouvenirCase emptySouvenirCase();
    ISkin emptySkin();
    IVault emptyVault();
    ISticker emptySticker();
}
