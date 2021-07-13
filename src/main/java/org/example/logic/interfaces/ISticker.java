package org.example.logic.interfaces;

import org.example.logic.interfaces.comps.Convertible;
import org.example.logic.interfaces.comps.Displayable;

public interface ISticker extends Displayable, Convertible<ISticker> {

    ISticker populate(long id, double initPrice, String name, String image, String stashLink);
}
