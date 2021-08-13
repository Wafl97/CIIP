package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IKey;

import java.util.List;

public interface IKeyDomain {

    List<IKey> readAllKeys();
    void createKey(IKey key);
    IKey readKey(long id);
    void updateKey(IKey key);
    void deleteKey(long id);
}
