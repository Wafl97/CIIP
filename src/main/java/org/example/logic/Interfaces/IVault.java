package org.example.logic.Interfaces;

import org.example.logic.Interfaces.Comps.Convertible;
import org.example.logic.Interfaces.Comps.Identifiable;

import java.util.Map;
import java.util.Set;
// FIXME: 05-07-2021 Add the ability to store Skin and Souvenir

public interface IVault extends Convertible<IVault>, Identifiable {

    String getName();
    void setName(String name);

    Map<ICapsule,Long> getAllContainers();
    void setAllItems(Map<ICapsule,Long> map);
    Set<ICapsule> getItems();
    void addItems(ICapsule item, long amount);
    void removeItem(ICapsule item);

    IVault populate(long id, String name);
}
