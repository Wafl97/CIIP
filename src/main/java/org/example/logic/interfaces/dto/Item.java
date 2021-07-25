package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.Displayable;

public interface Item<T> extends Displayable, Convertible<T> {
}
