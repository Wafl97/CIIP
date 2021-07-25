package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.Displayable;

public interface ISticker extends Displayable, Convertible<ISticker> {

    ISticker populate(long id, double initPrice, String name, String image, String stashLink);
}
