package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ISouvenirCase;

import java.util.List;

public interface ISouvenirCaseDomain {

    List<ISouvenirCase> readAllSouvenirCases();
    void createSouvenirCase(ISouvenirCase souvenirCase);
    ISouvenirCase readSouvenirCase(long id);
    void updateSouvenirCase(ISouvenirCase souvenirCase);
    void deleteSouvenirCase(long id);
}
