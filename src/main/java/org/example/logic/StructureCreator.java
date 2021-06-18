package org.example.logic;

import org.example.logic.Interfaces.Factory;

public final class StructureCreator implements Factory {

    private static StructureCreator instance;

    private StructureCreator(){}

    public static Factory getInstance() {
        return instance == null ? instance = new StructureCreator() : instance;
    }

    @Override
    public Capsule emptyCapsule() {
        return new Capsule();
    }

    @Override
    public Vault emptyVault() {
        return new Vault();
    }
}
