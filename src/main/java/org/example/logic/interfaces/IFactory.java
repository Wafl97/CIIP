package org.example.logic.interfaces;

import org.example.logic.dto.interfaces.comps.Transferable;
import org.example.util.Attributes;

public interface IFactory {

    // TODO: 18-08-2021 Maybe make many factories to each type?
    @SuppressWarnings({"rawtypes"})
    Transferable makeNew(Attributes TYPE);
}
