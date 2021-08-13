package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IPlayerModel;

import java.util.List;

public interface IPlayerModelDomain {

    List<IPlayerModel> readAllPlayerModels();
    void createPlayerModel(IPlayerModel playerModel);
    IPlayerModel readPlayerModel(long id);
    void updatePlayerModel(IPlayerModel playerModel);
    void deletePlayerModel(long id);
}
