package org.example.logic;

import org.example.logic.interfaces.*;

public final class StructureCreator implements Factory {

    private static StructureCreator instance;

    private StructureCreator(){}

    public static Factory getInstance() {
        return instance == null ? instance = new StructureCreator() : instance;
    }

    @Override
    public ICapsule emptyCapsule() {
        return new Capsule();
    }

    @Override
    public ISouvenirCase emptySouvenirCase() {
        return new SouvenirCase();
    }

    @Override
    public ISkin emptySkin() {
        return new Skin();
    }

    @Override
    public IVault emptyVault() {
        return new Vault();
    }
}
