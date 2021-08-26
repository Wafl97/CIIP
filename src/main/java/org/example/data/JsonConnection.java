package org.example.data;

import org.example.data.interfaces.DataConnection;

import org.example.data.interfaces.IGenericSubConnection;
import org.example.util.ConsoleColors;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.*;

import static org.example.util.Attributes.*;

final class JsonConnection implements DataConnection {

    //Data path
    private static final String PATH = "org/example/datacollection/";

    private static JsonConnection instance;
    private final Map<String,File> fileMap = new HashMap<>();
    private boolean connected = false;

    private JsonConnection(){
    }

    public static DataConnection getInstance() {
        return instance == null ? instance = new JsonConnection() : instance;
    }

    @Override
    public boolean connect(boolean print){
        if (!connected) {
            if (print) {
                System.out.println("\n||===========================================");
                System.out.println("|| " + ConsoleColors.PURPLE + "Initiating connection" + ConsoleColors.RESET);
                try {
                    // Get the path for the database
                    URL url = getClass().getClassLoader().getResource(PATH);
                    // Check if the path is valid
                    if (url == null) throw new FileNotFoundException("Directory: " + PATH + " does not exist.");
                    // Creates the a file object to the directory
                    File directory = new File(url.toURI());

                    if (!directory.isDirectory()) throw new NotDirectoryException(PATH + " is not a directory.");
                    System.out.println("|| Loading files from [" + ConsoleColors.BLUE + directory + ConsoleColors.RESET + "]");

                    // Get all files from directory
                    File[] files = directory.listFiles();

                    // Check if is directory
                    if (files == null) throw new NotDirectoryException(PATH + " is not a directory.");
                    System.out.println("|| Total files found [" + ConsoleColors.CYAN + files.length + ConsoleColors.RESET + "]");

                    // Populate tables hashmap
                    long totalBytes = 0;
                    for (File file : files) {
                        // Get name of file
                        String fileName = file.getName().split("\\.")[0];
                        System.out.println("|| " + ConsoleColors.YELLOW + "Found file " + ConsoleColors.RESET + "[" + ConsoleColors.BLUE + fileName + ConsoleColors.RESET + "] : Size [" + ConsoleColors.CYAN + file.length() + ConsoleColors.RESET + "] b");
                        totalBytes += file.length();

                        fileMap.put(fileName, file);
                    }

                    System.out.println("|| Total Size[" + ConsoleColors.CYAN + totalBytes + ConsoleColors.RESET + "] b");
                    System.out.println("|| " + ConsoleColors.GREEN + "Initialisation done" + ConsoleColors.RESET);
                    System.out.println("||===========================================\n");

                    connected = true;
                    return true;
                } catch (URISyntaxException | FileNotFoundException | NotDirectoryException e) {
                    connected = false;
                    return false;
                }
            }
            else {
                try {
                    // Get the path for the database
                    URL url = getClass().getClassLoader().getResource(PATH);
                    // Check if the path is valid
                    if (url == null) throw new FileNotFoundException("Directory: " + PATH + " does not exist.");
                    // Creates the a file object to the directory
                    File directory = new File(url.toURI());

                    if (!directory.isDirectory()) throw new NotDirectoryException(PATH + " is not a directory.");

                    // Get all files from directory
                    File[] files = directory.listFiles();

                    // Check if is directory
                    if (files == null) throw new NotDirectoryException(PATH + " is not a directory.");

                    // Populate tables hashmap
                    for (File file : files) {
                        // Get name of file
                        String fileName = file.getName().split("\\.")[0];

                        fileMap.put(fileName, file);
                    }
                    connected = true;
                    return true;
                } catch (URISyntaxException | FileNotFoundException | NotDirectoryException e) {
                    connected = false;
                    return false;
                }
            }
        }
        if (print) System.out.println(ConsoleColors.GREEN + "Connected"  + ConsoleColors.RESET);
        return true;
    }

    @Override
    public Map<String, File> getFileMap() {
        return fileMap;
    }

    @Override
    public IGenericSubConnection getCapsuleConnection() {
        return new GenericSubConnection(CAPSULES,CAPSULE,true);
    }

    @Override
    public IGenericSubConnection getCaseConnection() {
        return new GenericSubConnection(CASES,CASE,true);
    }

    @Override
    public IGenericSubConnection getGraffitiConnection() {
        return new GenericSubConnection(GRAFFITIES,GRAFFITI,true);
    }

    @Override
    public IGenericSubConnection getKeyConnection() {
        return new GenericSubConnection(KEYS,KEY,true);
    }

    @Override
    public IGenericSubConnection getMusicKitConnection() {
        return new GenericSubConnection(MUSICKITS,MUSICKIT,true);
    }

    @Override
    public IGenericSubConnection getPatchConnection() {
        return new GenericSubConnection(PATCHES,PATCH,true);
    }

    @Override
    public IGenericSubConnection getPinConnection() {
        return new GenericSubConnection(PINS,PIN,true);
    }

    @Override
    public IGenericSubConnection getPlayerModelConnection() {
        return new GenericSubConnection(PLAYERMODELS,PLAYERMODEL,true);
    }

    @Override
    public IGenericSubConnection getSkinConnection() {
        return new GenericSubConnection(SKINS,SKIN,true);
    }

    @Override
    public IGenericSubConnection getSouvenirConnection() {
        return new GenericSubConnection(SOUVENIRS,SOUVENIR,true);
    }

    @Override
    public IGenericSubConnection getStickerConnection() {
        return new GenericSubConnection(STICKERS,STICKER,true);
    }

    @Override
    public IGenericSubConnection getTicketConnection() {
        return new GenericSubConnection(TICKETS,TICKET,true);
    }

    @Override
    public IGenericSubConnection getVaultConnection() {
        return new GenericSubConnection(VAULTS,VAULT,false);
    }
}
