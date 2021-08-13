package org.example.data;

import org.example.data.interfaces.ILogWriter;
import org.example.util.ConsoleColors;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LogWriter implements ILogWriter {

    private static final String FILE_PATH = "org/example/progfiles/";
    private static final String LOG = "actions.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void writeLog(String log) {
        try {
            BufferedWriter output = new BufferedWriter(
                                    new FileWriter((
                                    new File(getClass().getClassLoader().getResource(FILE_PATH).toURI()) + "\\" + LOG),true));

            LocalDateTime now = LocalDateTime.now();
            String nowFormatted = now.format(FORMATTER);

            //Log msg
            output
                    .append("Time :: ")
                    .append(nowFormatted)
                    .append("\t Log :: ")
                    .append(log)
                    .append("\n");
            output.close();
            System.out.println(log);
        }
        catch (IOException | URISyntaxException e){
            System.out.println(ConsoleColors.RED + "-- Err when writing file --" + ConsoleColors.RESET);
            e.printStackTrace();
        }
    }

    @Override
    public String readLog() {
        return "DOES NOT WORK YET";
    }

    public static void main(String[] args) {
        LogWriter lw = new LogWriter();
        lw.writeLog("THIS IS A TEST");
    }
}
