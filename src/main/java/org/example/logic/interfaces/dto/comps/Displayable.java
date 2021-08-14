package org.example.logic.interfaces.dto.comps;

public interface Displayable extends Identifiable, Sellable {

    String getImage();
    void setImage(String image);
}
