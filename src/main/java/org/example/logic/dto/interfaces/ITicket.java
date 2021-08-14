package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.GenericPopulate;

public interface ITicket extends Displayable, Convertible<ITicket>, GenericPopulate<ITicket> {
}
