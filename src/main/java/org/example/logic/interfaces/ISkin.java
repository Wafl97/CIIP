package org.example.logic.interfaces;

import org.example.logic.interfaces.comps.Displayable;
import org.example.logic.interfaces.comps.Convertible;

public interface ISkin extends Displayable, Convertible<ISkin> {

    void setStatTrack(boolean statTrack);
    boolean isStatTrack();

    void setSouvenir(boolean souvenir);
    boolean isSouvenir();

    void setWearFloat(double wearFloat);
    double getWearFloat();

    ISkin populate(long id, double initPrice, String name, String image, String stashLink, double wearFloat, boolean statTrack, boolean souvenir);
}