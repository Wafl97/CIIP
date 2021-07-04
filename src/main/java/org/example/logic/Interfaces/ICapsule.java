package org.example.logic.Interfaces;

import org.example.logic.Interfaces.Comps.Collectable;
import org.example.logic.Interfaces.Comps.Convertible;
import org.example.logic.Interfaces.Comps.Identifiable;
import org.example.logic.Interfaces.Comps.Sellable;

public interface ICapsule extends Collectable, Convertible<ICapsule>, Sellable, Identifiable {

    ICapsule populate(long id, double initPrice, String name, String image, String stashLink);
}
