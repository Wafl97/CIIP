package org.example.logic;

import org.example.logic.Interfaces.Factory;

public class StructureCreator implements Factory {

    private static StructureCreator instance;

    private StructureCreator(){}

    public static Factory getInstance() {
        return instance == null ? instance = new StructureCreator() : instance;
    }

    @Override
    public Item emptyCapsule() {
        return new Item();
    }

    @Override
    public Vault emptyVault() {
        return new Vault();
    }
}
