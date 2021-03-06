package org.example.data;

import org.example.data.interfaces.DataConnection;
import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGFX;
import org.example.util.ConsoleColors;

import java.util.Collection;

public final class DataFacade implements IDataFacade {

    private static DataFacade instance;
    private final GFX GRAFIX;
    private final DataConnection CONNECTION;

    private DataFacade(){
        System.out.println("\n\n===========================================");
        System.out.println(ConsoleColors.PURPLE + "Staring Persistence" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW + "\t - Getting GFX" + ConsoleColors.RESET);
        GRAFIX = GFX.getInstance();

        System.out.println(ConsoleColors.YELLOW + "\t - Getting DataConnection" + ConsoleColors.RESET);
        CONNECTION = JsonConnection.getInstance();
        System.out.println("\t\t - Connection type: ["  + ConsoleColors.BLUE + CONNECTION.getClass().getSimpleName()  + ConsoleColors.RESET + "]");
        System.out.println(ConsoleColors.YELLOW + "\t - Running Connection:" + ConsoleColors.RESET);
        String val = CONNECTION.connect(true) ? ConsoleColors.GREEN : ConsoleColors.RED;
        System.out.println("\t - Connection success: [" + val + CONNECTION.connect(true) + ConsoleColors.RESET + "]");

        System.out.println(ConsoleColors.GREEN + "Persistence Done\n\n" + ConsoleColors.RESET);
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
}
