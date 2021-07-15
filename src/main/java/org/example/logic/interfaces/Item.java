package org.example.logic.interfaces;

import org.example.logic.interfaces.comps.Convertible;
import org.example.logic.interfaces.comps.Displayable;

public interface Item<T> extends Displayable, Convertible<T> {
}
