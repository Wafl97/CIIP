package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Convertible;
import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.Identifiable;

import java.util.Map;
import java.util.Set;

public interface IVault extends Convertible<IVault>, Identifiable {

    Map<Displayable,Long> getAllItems();
    void setAllItems(Map<Displayable,Long> map);
    Set<Displayable> getItems();
    void addItems(Displayable item, long amount);
    void removeItem(Displayable item);

    IVault populate(long id, String name);
}