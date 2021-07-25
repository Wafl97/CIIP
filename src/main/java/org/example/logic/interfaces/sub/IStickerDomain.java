package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ISticker;

import java.util.List;

public interface IStickerDomain {

    List<ISticker> readAllStickers();
    void createSticker(ISticker sticker);
    ISticker readSticker(long id);
    void updateSticker(ISticker sticker);
    void deleteSticker(long id);
}
