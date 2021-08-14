package org.example.data;

import org.example.data.interfaces.ILogWriter;
import org.example.util.ConsoleColors;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        }
        catch (IOException | URISyntaxException e){
            System.out.println(ConsoleColors.RED + "-- Err when writing file --" + ConsoleColors.RESET);
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readLog() {
        List<String> log = new ArrayList<>();
        try{
            BufferedReader input = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource(FILE_PATH).toURI()) + "\\" + LOG));
            String line;
            while ((line = input.readLine()) != null){
                log.add(line);
            }
            input.close();
        }
        catch (IOException | URISyntaxException e){
            System.out.println(ConsoleColors.RED + "-- Err when reading file --" + ConsoleColors.RESET);
            e.printStackTrace();
        }
        return log;
    }

    public static void main(String[] args) {
        LogWriter lw = new LogWriter();
        lw.writeLog("THIS IS A TEST");
        List<String> sa = lw.readLog();
        for (String s : sa){
            System.out.println(s);
        }
    }
}
