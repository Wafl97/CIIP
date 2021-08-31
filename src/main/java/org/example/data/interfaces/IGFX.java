package org.example.data.interfaces;

import java.io.File;
import java.util.Map;

public interface IGFX {

    void init(boolean print);

    File getLogo();

    Map<String, File> getImageMap();

    void uploadImage(File imageFile);
}
