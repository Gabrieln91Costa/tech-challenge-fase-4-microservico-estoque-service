package com.microservico.estoqueservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueTest {

    @Test
    void deveCriarEstoqueComGettersESetters() {
        Estoque estoque = new Estoque();
        estoque.setId("id123");
        estoque.setSku("SKU-001");
        estoque.setQuantidade(100);

        assertEquals("id123", estoque.getId());
        assertEquals("SKU-001", estoque.getSku());
        assertEquals(100, estoque.getQuantidade());
    }

    @Test
    void deveCriarEstoqueComConstrutorCompleto() {
        Estoque estoque = new Estoque("id456", "SKU-002", 50);

        assertEquals("id456", estoque.getId());
        assertEquals("SKU-002", estoque.getSku());
        assertEquals(50, estoque.getQuantidade());
    }

    @Test
    void devePermitirAlteracaoDeQuantidade() {
        Estoque estoque = new Estoque("id789", "SKU-003", 30);
        estoque.setQuantidade(60);

        assertEquals(60, estoque.getQuantidade());
    }

    @Test
    void deveCriarEstoqueUsandoConstrutorVazioEsetters() {
        Estoque estoque = new Estoque();
        estoque.setSku("SKU-004");
        estoque.setQuantidade(75);

        assertNull(estoque.getId());  // id não definido
        assertEquals("SKU-004", estoque.getSku());
        assertEquals(75, estoque.getQuantidade());
    }
}
