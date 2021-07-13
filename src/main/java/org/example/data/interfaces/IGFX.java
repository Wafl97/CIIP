package org.example.data.interfaces;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Map;

public interface IGFX {

    Image getLogo();

    Map<String, Image> getImageMap();

    void loadImages(boolean clear);

    void uploadImage(File imageFile);
}
