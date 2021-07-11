package org.example.data;

import java.io.*;

public final class DataReset {

    private static DataReset instance;

    public static void main(String[] args) {
        DataReset dataReset = new DataReset();
        dataReset.reset();
    }

    public static DataReset getInstance(){
        return instance == null ? instance = new DataReset() : instance;
    }

    private DataReset(){}

    public void reset() {
        File sour = new File (DataReset.class.getResource("/org/example/backupdata/").toString().split("file:")[1]);
        File dest = new File (DataReset.class.getResource("/org/example/datacollection/").toString().split("file:")[1]);

        copyFile(sour,dest);
    }

    private void copyFile(File src, File dest){
        try {
            for (int i = 0; i < src.listFiles().length; i++) {
                FileInputStream srcReader = new FileInputStream(src.listFiles()[i]);
                FileOutputStream destWriter = new FileOutputStream(dest.listFiles()[i]);

                byte[] data = new byte[(int) src.listFiles()[i].length()];
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
