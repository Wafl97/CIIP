package org.example.logic;

import org.example.data.DataFacade;
import org.example.logic.Interfaces.IFileHandler;

import java.io.File;

public final class FileHandler implements IFileHandler {

    private static FileHandler instance;

    private FileHandler(){
    }

    public static FileHandler getInstance(){
        return instance == null ? instance = new FileHandler() : instance;
    }

    @Override
    public void save(File file) {
        DataFacade.getInstance().getGFX().uploadImage(file);
    }

    @Override
    public File load(String fileName) {
        return null;
    }
}
