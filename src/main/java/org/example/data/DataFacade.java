package org.example.data;

import org.example.data.interfaces.DataConnection;
import org.example.data.interfaces.IDataFacade;
import org.example.data.interfaces.IGFX;

public class DataFacade implements IDataFacade {

    private static DataFacade instance;

    private DataFacade(){}

    public static DataFacade getInstance(){
        return instance == null ? instance = new DataFacade() : instance;
    }


    @Override
    public DataConnection getDataConnection() {
        return JsonConnection.getInstance();
    }

    @Override
    public IGFX getGFX() {
        return GFX.getInstance();
    }
}
