package org.example.logic.Interfaces;

import org.example.logic.Capsule;
import org.example.logic.Vault;

public interface Factory {

    ICapsule emptyCapsule();
    ISouvenirCase emptySouvenirCase();
    ISkin emptySkin();
    IVault emptyVault();
}
