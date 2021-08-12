package org.example.logic.interfaces.dto.comps;

public interface GenericPopulate<T> {

    T populate(long id, double initPrice, String name, String image, String stashLink);
}
