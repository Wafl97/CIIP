package org.example.data;

import org.example.data.interfaces.*;
import org.example.util.ConsoleColors;

public final class DataFacade implements IDataFacade {

    private static DataFacade instance;
    private static IGFX GRAFIX;
    private static DataConnection CONNECTION;
    private static ILogWriter LOGGER;

    private DataFacade(){

    }

    @Override
    public void init(boolean print, boolean loadGFX) {
        if (print) {
            System.out.println("\n\n===========================================");
            System.out.println(ConsoleColors.PURPLE + "Staring Persistence" + ConsoleColors.RESET);

            System.out.println(ConsoleColors.YELLOW + "\t - Getting LogWriter" + ConsoleColors.RESET);
            LOGGER = LogWriter.getInstance();

            if (loadGFX) {
                System.out.println(ConsoleColors.YELLOW + "\t - Getting GFX" + ConsoleColors.RESET);
                GRAFIX = GFX.getInstance();
                GRAFIX.init(true);
            }

            System.out.println(ConsoleColors.YELLOW + "\t - Getting DataConnection" + ConsoleColors.RESET);
            CONNECTION = JsonConnection.getInstance();
            System.out.println("\t\t - Connection type: [" + ConsoleColors.BLUE + CONNECTION.getClass().getSimpleName() + ConsoleColors.RESET + "]");
            System.out.println(ConsoleColors.YELLOW + "\t - Running Connection:" + ConsoleColors.RESET);
            String val = CONNECTION.connect(true) ? ConsoleColors.GREEN : ConsoleColors.RED;
            System.out.println("\t - Connection success: [" + val + CONNECTION.connect(true) + ConsoleColors.RESET + "]");

            System.out.println(ConsoleColors.GREEN + "Persistence Done\n\n" + ConsoleColors.RESET);
        }
        else {
            LOGGER = LogWriter.getInstance();

            if (loadGFX) {
                GRAFIX = GFX.getInstance();
                GRAFIX.init(false);
            }
            CONNECTION = JsonConnection.getInstance();
            CONNECTION.connect(false);
        }
    }

    public static DataFacade getInstance(){
        return instance == null ? instance = new DataFacade() : instance;
    }


    @Override
    public DataConnection getDataConnection() {
        return CONNECTION;
    }

    @Override
    public IGFX getGFX() {
        return GRAFIX;
    }

    @Override
    public ILogWriter getLogWriter() {
        return LOGGER;
    }
}
