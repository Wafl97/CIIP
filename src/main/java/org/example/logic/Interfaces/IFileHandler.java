package org.example.logic.Interfaces;

import java.io.File;

public interface IFileHandler {
    void save(File file);

    File load(String fileName);
}
