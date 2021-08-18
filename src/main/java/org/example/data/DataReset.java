package org.example.data;

import java.io.*;
import java.util.Objects;

public final class DataReset {

    private static DataReset instance;

    public static DataReset getInstance(){
        return instance == null ? instance = new DataReset() : instance;
    }

    private DataReset(){}

    public void reset() {
        File sour = new File (Objects.requireNonNull(DataReset.class.getResource("/org/example/backupdata/")).toString().split("file:")[1]);
        File dest = new File (Objects.requireNonNull(DataReset.class.getResource("/org/example/datacollection/")).toString().split("file:")[1]);

        copyFile(sour,dest);
    }

    private void copyFile(File src, File dest){
        try {
            for (int i = 0; i < Objects.requireNonNull(src.listFiles()).length; i++) {
                FileInputStream srcReader = new FileInputStream(Objects.requireNonNull(src.listFiles())[i]);
                FileOutputStream destWriter = new FileOutputStream(Objects.requireNonNull(dest.listFiles())[i]);

                byte[] data = new byte[(int) Objects.requireNonNull(src.listFiles())[i].length()];
                srcReader.read(data);
                srcReader.close();

                destWriter.write(data);
                destWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
