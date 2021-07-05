package org.example.logic.interfaces;

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.comps.Convertible;

public interface ICapsule extends Displayable, Convertible<ICapsule> {

    ICapsule populate(long id, double initPrice, String name, String image, String stashLink);
}
