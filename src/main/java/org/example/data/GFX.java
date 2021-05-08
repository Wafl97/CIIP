package org.example.data;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * This class loads and holds all the graphical elements
 * It is a singleton, so it will only instantiate once
 */
public class GFX {

    /**
     * Directory path
     */
    private static final String DIR = "/org/example/gfx/";
    /**
     * Saved directory path
     */
    private static final String S_DIR = "/org/example/gfx/saved";

    /**
     * App icon
     */
    private Image logo;
    /**
     * App icon
     */
    private static final String LOGO_PATH = "/org/example/gfx/icon.png";


    private final Map<String,Image> imageMap = new HashMap<>();

    /**
     * Singleton instance
     */
    private static GFX instance;

    public static GFX getInstance(){
        return instance == null ? instance = new GFX() : instance;
    }

    public GFX() {
        try {
            logo = new Image(GFX.class.getResource(LOGO_PATH).toURI().toString());
            String s = GFX.class.getResource(S_DIR).toURI().toString();
            String[] sA = s.split(":");
            String path = sA[1] + ":" +  sA[2];
            File f = new File(path);
            File[] fA = f.listFiles();
            for (File file : fA){
                imageMap.put(file.getName(),new Image(file.toURI().toString()));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Image getLogo(){
        return logo;
    }

    public Map<String, Image> getImageMap() {
        return imageMap;
    }
}
