package org.example.data.interfaces;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DataConnection {

    boolean connect(boolean print);

    Map<String, File> getFileMap();

    IGenericSubConnection getCapsuleConnection();
    IGenericSubConnection getCaseConnection();
    IGenericSubConnection getGraffitiConnection();
    IGenericSubConnection getKeyConnection();
    IGenericSubConnection getMusicKitConnection();
    IGenericSubConnection getPatchConnection();
    IGenericSubConnection getPinConnection();
    IGenericSubConnection getPlayerModelConnection();
    IGenericSubConnection getSkinConnection();
    IGenericSubConnection getSouvenirConnection();
    IGenericSubConnection getStickerConnection();
    IGenericSubConnection getTicketConnection();
    IGenericSubConnection getVaultConnection();
}
