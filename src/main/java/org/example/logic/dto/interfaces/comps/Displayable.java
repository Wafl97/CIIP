package org.example.logic.dto.interfaces.comps;

public interface Displayable extends Identifiable, Sellable {

    String getImage();
    void setImage(String image);
}
