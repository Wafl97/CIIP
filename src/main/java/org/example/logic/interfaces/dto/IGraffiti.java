package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.GenericPopulate;

public interface IGraffiti extends Displayable, Convertible<IGraffiti>, GenericPopulate<IGraffiti> {
}
