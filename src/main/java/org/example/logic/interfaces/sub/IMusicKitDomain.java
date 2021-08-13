package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IMusicKit;

import java.util.List;

public interface IMusicKitDomain {

    List<IMusicKit> readAllMusicKits();
    void createMusicKit(IMusicKit musicKit);
    IMusicKit readMusicKit(long id);
    void updateMusicKit(IMusicKit musicKit);
    void deleteMusicKit(long id);
}
