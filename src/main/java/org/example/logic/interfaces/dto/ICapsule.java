package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.GenericPopulate;

public interface ICapsule extends Displayable, Convertible<ICapsule>, GenericPopulate<ICapsule> {
}
