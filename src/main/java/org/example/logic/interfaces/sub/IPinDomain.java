package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IPin;

import java.util.List;

public interface IPinDomain {

    List<IPin> readAllPins();
    void createPin(IPin pin);
    IPin readPin(long id);
    void updatePin(IPin pin);
    void deletePin(long id);
}
