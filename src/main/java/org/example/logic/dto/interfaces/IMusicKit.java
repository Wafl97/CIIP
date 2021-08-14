package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.GenericPopulate;

public interface IMusicKit extends Displayable, Convertible<IMusicKit>, GenericPopulate<IMusicKit> {
}
