package org.example.logic.Interfaces;

import org.example.logic.Interfaces.Comps.Collectable;
import org.example.logic.Interfaces.Comps.Convertible;
import org.example.logic.Interfaces.Comps.Identifiable;
import org.example.logic.Interfaces.Comps.Sellable;

public interface ISkin extends Collectable, Convertible<ISkin>, Sellable, Identifiable {

    void setStatTrack(boolean statTrack);
    boolean isStatTrack();

    void setSouvenir(boolean souvenir);
    boolean isSouvenir();

    void setWearFloat(float wearFloat);
    float getWearFloat();

    ISkin populate(long id, double initPrice, String name, String image, String stashLink);
}