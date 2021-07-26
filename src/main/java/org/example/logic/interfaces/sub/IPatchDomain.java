package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.IPatch;

import java.util.List;

public interface IPatchDomain {

    List<IPatch> readAllPatches();
    void createPatch(IPatch patch);
    IPatch readPatch(long id);
    void updatePatch(IPatch patch);
    void deletePatch(long id);
}
