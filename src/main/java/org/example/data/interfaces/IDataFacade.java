package org.example.data.interfaces;

public interface IDataFacade {

    void init(boolean print, boolean loadGFX);

    DataConnection getDataConnection();

    IGFX getGFX();

    ILogWriter getLogWriter();
}
