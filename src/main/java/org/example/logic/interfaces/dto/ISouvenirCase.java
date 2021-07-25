package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Convertible;

public interface ISouvenirCase extends Displayable, Convertible<ISouvenirCase> {

    ISouvenirCase populate(long id, double initPrice, String name, String image, String stashLink);
}
