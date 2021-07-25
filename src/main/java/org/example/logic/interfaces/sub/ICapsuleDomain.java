package org.example.logic.interfaces.sub;

import org.example.logic.interfaces.dto.ICapsule;

import java.util.List;

public interface ICapsuleDomain {

    List<ICapsule> readAllCapsules();
    void createCapsule(ICapsule capsule);
    ICapsule readCapsule(long id);
    void updateCapsule(ICapsule capsule);
    void deleteCapsule(long id);
}
