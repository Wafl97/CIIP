package org.example.logic;

import org.example.logic.interfaces.IActionWriter;

public final class ActionWriter implements IActionWriter {

    @Override
    public void printAction(String color, String action, String dataType, long id) {
        System.out.println(color + "-- " + action + " :: " + dataType + " with id: " + id + " --" + R);
    }
}
