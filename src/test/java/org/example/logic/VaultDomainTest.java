package org.example.logic;

import org.example.logic.dto.interfaces.IVault;
import org.example.logic.dto.interfaces.comps.Transferable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.example.logic.TestData.m1;
import static org.example.logic.TestData.m2;
import static org.example.util.Attributes.VAULT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VaultDomainTest extends GenericDomainTest{

    @Test
    void readAllVaults() {
        goodList = new ArrayList<>(Arrays.asList(m1,m2));
        List<Transferable<IVault>> mList = domain.getVaultDomain().readAll();
        IntStream.range(0, goodList.size()).forEach(i -> assertEquals(goodList.get(i).toString(), mList.get(i).toString()));
    }

    @Test
    void createVault() {
        IVault m7 = ((IVault) factory.makeNew(VAULT)).populate(-1,"new vault");
        assertTrue(domain.getVaultDomain().create(m7));
    }

    @Test
    void readVault() {
        assertEquals(m1.toString(),domain.getVaultDomain().read(1).toString());
    }

    @Test
    void updateVault() {
        m1.setName("Berlin++");
        assertTrue(domain.getVaultDomain().update(m1));
        m1.setName("Berlin");
    }

    @Test
    void deleteVault() {
        assertTrue(domain.getVaultDomain().delete(m1.findMaxID()));
    }
}
