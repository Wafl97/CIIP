package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Convertible;

public interface ICapsule extends Displayable, Convertible<ICapsule> {

    ICapsule populate(long id, double initPrice, String name, String image, String stashLink);
}
