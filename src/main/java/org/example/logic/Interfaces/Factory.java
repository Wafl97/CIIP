package org.example.logic.Interfaces;

import org.example.logic.Capsule;
import org.example.logic.Vault;
import org.json.simple.JSONObject;

public interface Factory {

    Capsule emptyCapsule();
    Vault emptyVault();
}
