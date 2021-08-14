package org.example.logic;

import org.example.data.DataFacade;
import org.example.data.interfaces.ILogWriter;
import org.example.logic.interfaces.IActionWriter;

public final class ActionWriter implements IActionWriter {

    private final ILogWriter logWriter = DataFacade.getInstance().getLogWriter();

    private static ActionWriter instance;

    private ActionWriter(){

    }

    public static ActionWriter getInstance(){
        return instance == null ? instance = new ActionWriter() : instance;
    }

    @Override
    public void printAction(String color, String action, String dataType, long id, boolean log) {
        //Build msg
        String log_msg = "-- " + action + " :: " + dataType + " with id: " + id + " --";

        System.out.println(color + log_msg + R);
        if (log) {
            logWriter.writeLog(log_msg);
        }
    }
}
