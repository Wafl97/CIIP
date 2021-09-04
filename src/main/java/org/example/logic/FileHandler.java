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
        Domain.getInstance().getActionWriter().printAction(GREEN,"SAVE","File",-1,true);
        System.out.println("IMAGE MAP :: " + DataFacade.getInstance().getGFX().getImageMap().size());
        DataFacade.getInstance().getGFX().uploadImage(file);
        System.out.println("IMAGE MAP :: " + DataFacade.getInstance().getGFX().getImageMap().size());
    }

    @Override
    public File load(String fileName) {
        Domain.getInstance().getActionWriter().printAction(PURPLE,"LOAD","File",-1,true);
        return DataFacade.getInstance().getGFX().getImageMap().get(fileName);
    }
}
