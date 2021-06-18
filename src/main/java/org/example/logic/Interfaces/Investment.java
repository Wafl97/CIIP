package org.example.logic.Interfaces;

import java.util.Map;
import java.util.Set;

public interface Investment {

    long getId();
    void setId(long id);

    String getName();
    void setName(String name);

    Map<IItem,Long> getAllContainers();
    void setAllItems(Map<IItem,Long> map);
    Set<IItem> getItems();
    void addItems(IItem item, long amount);
    void removeItem(IItem item);

    Investment populate(long id, String name);

}
