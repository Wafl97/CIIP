package org.example.data;

import javafx.scene.image.Image;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * This class loads and holds all the graphical elements
 * It is a singleton, so not to load the assets more than once
 */
public class GFX implements IGFX{

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
    private Image logo;

    private final Map<String,Image> imageMap = new HashMap<>();

    /**
     * Singleton instance
     */
    private static GFX instance;

    public static GFX getInstance(){
        return instance == null ? instance = new GFX() : instance;
    }

    private GFX() {
        try {
            logo = new Image(GFX.class.getResource(LOGO_PATH).toURI().toString());
            File f = new File(GFX.class.getResource(S_DIR).toURI());
            File[] fArr = f.listFiles();
            if (fArr != null) {
                for (File file : fArr) {
                    imageMap.put(file.getName(), new Image(file.toURI().toString()));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reloadImages(boolean clear){
        if (clear) {
            imageMap.clear();
        }
        try {
            File f = new File(GFX.class.getResource(S_DIR).toURI());
            File[] fArr = f.listFiles();
            if (fArr != null) {
                for (File file : fArr) {
                    imageMap.put(file.getName(), new Image(file.toURI().toString()));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadImage(File imageFile) {
        try {
            //Make new file in program
            File dirPath = new File(getClass().getClassLoader().getResource("org/example/gfx/saved/").toURI());
            String imageFileName = imageFile.getName().replaceAll("\\s","_").replaceAll("%20","_"); //Remove Spaces and '%20';
            File newFile = new File(dirPath + imageFileName);
            if (!newFile.exists()) newFile.createNewFile();

            //Copy the old file the the new empty file
            COPY_FILE(imageFile,newFile);

            //Cache the image
            //reloadImages(false);
            System.out.println(cacheImage(imageFileName,new Image(newFile.toURI().toString())));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean cacheImage(String imageName, Image image){
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
    public Image getLogo(){
        return logo;
    }

    @Override
    public Map<String, Image> getImageMap() {
        return imageMap;
    }
}
