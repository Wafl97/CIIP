package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ICapsule;
import org.example.logic.interfaces.dto.ICase;

import java.util.List;

public interface ICaseDomain {

    List<ICase> readAllCases();
    void createCase(ICase Case);
    ICase readCase(long id);
    void updateCase(ICase Case);
    void deleteCase(long id);
}
