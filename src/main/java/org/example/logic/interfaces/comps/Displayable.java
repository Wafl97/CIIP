package org.example.logic.interfaces.comps;

public interface Displayable extends Identifiable, Sellable {

    String getName();
    void setName(String name);

    String getImage();
    void setImage(String image);
}
