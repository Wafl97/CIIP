package org.example.logic.interfaces;

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.comps.Convertible;

public interface ISouvenirCase extends Displayable, Convertible<ISouvenirCase> {

    ISouvenirCase populate(long id, double initPrice, String name, String image, String stashLink);
}
