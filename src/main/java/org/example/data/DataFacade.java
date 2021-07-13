package org.example.data;

import org.example.data.interfaces.DataConnection;
import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGFX;

public final class DataFacade implements IDataFacade {

    private static DataFacade instance;
    private final GFX GRAFIX;
    private final DataConnection CONNECTION;

    private DataFacade(){
        System.out.println("Staring Persistence");

        System.out.println("\t - Getting GFX");
        GRAFIX = GFX.getInstance();

        System.out.println("\t - Getting DataConnection");
        CONNECTION = JsonConnection.getInstance();
        System.out.println("\t - Connection type: [" + CONNECTION.getClass().getSimpleName() + "]");
        System.out.println("\t - Running Connection:");
        CONNECTION.connect(true);
        System.out.println("\t - Connection success: [" + CONNECTION.connect(true) + "]");

        System.out.println("Persistence Done");
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
