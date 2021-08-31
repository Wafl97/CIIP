package org.example.data;

import org.example.data.interfaces.IGFX;
import org.example.util.ConsoleColors;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * This class loads and holds all the graphical elements
 * It is a singleton, so not to load the assets more than once
 */
final class GFX implements IGFX {

    /**
     * Directory path
     */
    private static final String DIR = "/org/example/gfx/";
    /**
     * Saved directory path
     */
    private static final String S_DIR = DIR+"saved";

    /**
     * App icon
     */
    private static final String LOGO_PATH = DIR+"icon.png";

    /**
     * App icon
     */
    private File logo;

    private final Map<String,File> imageMap = new HashMap<>();

    /**
     * Singleton instance
     */
    private static GFX instance;

    public static GFX getInstance(){
        return instance == null ? instance = new GFX() : instance;
    }

    private GFX() {

    }

    public void init(boolean print){
        if (print){
            System.out.println("||===========================================");
            System.out.println("|| " + ConsoleColors.PURPLE + "Initialising GFX" + ConsoleColors.RESET);
            loadLogo(true);
            loadImages(true);
            System.out.println("|| " + ConsoleColors.GREEN + "Initialisation done" + ConsoleColors.RESET);
            System.out.println("||===========================================");
        }
        else{
            loadLogo(false);
            loadImages(false);
        }
    }

    private void loadLogo(boolean print){
        try {
            logo = new File(Objects.requireNonNull(GFX.class.getResource(LOGO_PATH)).toURI());
            if (print) System.out.println("|| Logo found at [" + ConsoleColors.BLUE + logo.getPath() + ConsoleColors.RESET + "]");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadImages(boolean print){
        try {
            File f = new File(Objects.requireNonNull(GFX.class.getResource(S_DIR)).toURI());
            if (print) System.out.println("|| Loading images from [" + ConsoleColors.BLUE + f + ConsoleColors.RESET + "]");
            File[] fArr = f.listFiles();
            if (fArr != null) {
                if (print) System.out.println("|| Total images found [" + ConsoleColors.CYAN + fArr.length + ConsoleColors.RESET + "]");
                for (File file : fArr) {
                    if (print) System.out.println("|| " + ConsoleColors.YELLOW + "Found image " + ConsoleColors.RESET + "[" + ConsoleColors.BLUE + file.getName() + ConsoleColors.RESET + "]");
                    imageMap.put(file.getName(), file);
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void uploadImage(File imageFile) {
        try {
            //Make new file in program
            File dirPath = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("org/example/gfx/saved/")).toURI());
            String imageFileName = imageFile.getName().replaceAll("\\s","_").replaceAll("%20","_"); //Remove Spaces and '%20';
            File newFile = new File(dirPath + imageFileName);
            if (!newFile.exists()) newFile.createNewFile();

            //Copy the old file the new empty file
            COPY_FILE(imageFile,newFile);

            //Cache the image
            //reloadImages(false);
            System.out.println(cacheImage(imageFileName,newFile));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean cacheImage(String imageName, File image){
        if (imageMap.containsKey(imageName)) return false;

        imageMap.put(imageName,image);
        return true;
    }

    private static void COPY_FILE(File source, File destination){
        try (BufferedInputStream biStream = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream boStream = new BufferedOutputStream(new FileOutputStream(destination))) {
            int length = 0;
            while (length != -1) {
                length = biStream.read();
                boStream.write(length);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public File getLogo(){
        return logo;
    }

    @Override
    public Map<String, File> getImageMap() {
        return imageMap;
    }
}
