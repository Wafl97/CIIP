package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;

public interface Item<T> extends Displayable, Convertible<T> {
}
