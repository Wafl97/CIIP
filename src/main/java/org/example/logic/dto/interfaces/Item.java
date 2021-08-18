package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.GenericPopulate;
import org.example.logic.dto.interfaces.comps.Transferable;

public interface Item<T> extends Displayable, Transferable<T>, GenericPopulate<T> {
}
