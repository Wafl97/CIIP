package org.example.logic.interfaces.dto;

import org.example.logic.interfaces.dto.comps.Convertible;
import org.example.logic.interfaces.dto.comps.Displayable;
import org.example.logic.interfaces.dto.comps.Identifiable;

import java.util.Map;
import java.util.Set;

public interface IVault extends Convertible<IVault>, Identifiable {

    String getName();
    void setName(String name);

    Map<Displayable,Long> getAllItems();
    void setAllItems(Map<Displayable,Long> map);
    Set<Displayable> getItems();
    void addItems(Displayable item, long amount);
    void removeItem(Displayable item);

    IVault populate(long id, String name);
}
