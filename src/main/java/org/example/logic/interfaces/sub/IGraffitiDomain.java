package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IGraffiti;

import java.util.List;

public interface IGraffitiDomain {

    List<IGraffiti> readAllGraffities();
    void createGraffiti(IGraffiti graffiti);
    IGraffiti readGraffiti(long id);
    void updateKGraffiti(IGraffiti graffiti);
    void deleteGraffiti(long id);
}
