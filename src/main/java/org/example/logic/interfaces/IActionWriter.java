package org.example.logic.interfaces;

import org.example.util.ConsoleColors;

public interface IActionWriter {

    String R = ConsoleColors.RESET;

    void printAction(String color, String action, String dataType, long id, boolean log);
}
