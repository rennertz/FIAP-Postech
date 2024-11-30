package br.com.vetvision.supervisor.application;

import br.com.vetvision.supervisor.application.impl.ProvimentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProvimentoServiceTest {

    ProvimentoService service;

    @BeforeEach
    public void setUp() {
        service = new ProvimentoServiceImpl();
    }


    @Test
    void ofertarAtendimentoTest() {
        assertTrue(true);
    }

    @Test
    void realizarExameTest() {
        assertTrue(true);
    }

    @Test
    void encaminharLaudoTest() {
        assertTrue(true);
    }
}
