package org.example.logic.dto.interfaces;

import org.example.logic.dto.interfaces.comps.Displayable;
import org.example.logic.dto.interfaces.comps.Convertible;

public interface ISkin extends Displayable, Convertible<ISkin> {

    void setStatTrak(boolean statTrak);
    boolean isStatTrak();

    void setSouvenir(boolean souvenir);
    boolean isSouvenir();

    void setWearFloat(double wearFloat);
    double getWearFloat();

    ISkin populate(long id, double initPrice, double currPrice, String name, String image, String stashLink, double wearFloat, boolean statTrack, boolean souvenir);
}