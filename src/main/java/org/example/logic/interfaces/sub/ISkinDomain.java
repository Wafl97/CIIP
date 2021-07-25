package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ISkin;

import java.util.List;

public interface ISkinDomain {

    List<ISkin> readAllSkins();
    void createSkin(ISkin skin);
    ISkin readSkin(long id);
    void updateSkin(ISkin skin);
    void deleteSkin(long id);
}
