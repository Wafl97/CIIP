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

    private final IGenericSubConnection CAPSULE_CON         = new GenericSubConnection(CAPSULES,CAPSULE,true);
    private final IGenericSubConnection CASE_CON            = new GenericSubConnection(CASES,CASE,true);
    private final IGenericSubConnection GRAFFITI_CON        = new GenericSubConnection(GRAFFITIES,GRAFFITI,true);
    private final IGenericSubConnection KEY_CON             = new GenericSubConnection(KEYS,KEY,true);
    private final IGenericSubConnection MUSIC_KIT_CON       = new GenericSubConnection(MUSICKITS,MUSICKIT,true);
    private final IGenericSubConnection PATCH_CON           = new GenericSubConnection(PATCHES,PATCH,true);
    private final IGenericSubConnection PIN_CON             = new GenericSubConnection(PINS,PIN,true);
    private final IGenericSubConnection PLAYER_MODEL_CON    = new GenericSubConnection(PLAYERMODELS,PLAYERMODEL,true);
    private final IGenericSubConnection SKIN_CON            = new GenericSubConnection(SKINS,SKIN,true);
    private final IGenericSubConnection SOUVENIR_CON        = new GenericSubConnection(SOUVENIRS,SOUVENIR,true);
    private final IGenericSubConnection STICKER_CON         = new GenericSubConnection(STICKERS,STICKER,true);
    private final IGenericSubConnection TICKET_CON          = new GenericSubConnection(TICKETS,TICKET,true);
    private final IGenericSubConnection VAULT_CON           = new GenericSubConnection(VAULTS,VAULT,false);

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
                    // Creates the file object to the directory
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
                    // Creates the file object to the directory
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
        return CAPSULE_CON;
    }

    @Override
    public IGenericSubConnection getCaseConnection() {
        return CASE_CON;
    }

    @Override
    public IGenericSubConnection getGraffitiConnection() {
        return GRAFFITI_CON;
    }

    @Override
    public IGenericSubConnection getKeyConnection() {
        return KEY_CON;
    }

    @Override
    public IGenericSubConnection getMusicKitConnection() {
        return MUSIC_KIT_CON;
    }

    @Override
    public IGenericSubConnection getPatchConnection() {
        return PATCH_CON;
    }

    @Override
    public IGenericSubConnection getPinConnection() {
        return PIN_CON;
    }

    @Override
    public IGenericSubConnection getPlayerModelConnection() {
        return PLAYER_MODEL_CON;
    }

    @Override
    public IGenericSubConnection getSkinConnection() {
        return SKIN_CON;
    }

    @Override
    public IGenericSubConnection getSouvenirConnection() {
        return SOUVENIR_CON;
    }

    @Override
    public IGenericSubConnection getStickerConnection() {
        return STICKER_CON;
    }

    @Override
    public IGenericSubConnection getTicketConnection() {
        return TICKET_CON;
    }

    @Override
    public IGenericSubConnection getVaultConnection() {
        return VAULT_CON;
    }
}
