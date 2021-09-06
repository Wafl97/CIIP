package org.example.logic;

import org.example.data.DataFacade;
import org.example.logic.interfaces.IFileHandler;

import java.io.File;
import static org.example.util.ConsoleColors.*;

public final class FileHandler implements IFileHandler {

    private static FileHandler instance;

    private FileHandler(){
    }

    public static FileHandler getInstance(){
        return instance == null ? instance = new FileHandler() : instance;
    }

    @Override
    public void save(File file) {
        Domain.getInstance().getActionWriter().printAction(GREEN,"SAVE","File",file.getName(),true);
        DataFacade.getInstance().getGFX().uploadImage(file);
    }

    @Override
    public File load(String fileName) {
        Domain.getInstance().getActionWriter().printAction(PURPLE,"LOAD","File",fileName,true);
        return DataFacade.getInstance().getGFX().getImageMap().get(fileName);
    }
}
