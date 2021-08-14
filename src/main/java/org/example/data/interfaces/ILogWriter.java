package org.example.data.interfaces;

import java.util.List;

public interface ILogWriter {

    void writeLog(String log);

    List<String> readLog();
}
