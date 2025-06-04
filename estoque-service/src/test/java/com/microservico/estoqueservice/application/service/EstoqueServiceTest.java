package com.microservico.estoqueservice.application.service;

import com.microservico.estoqueservice.domain.model.Estoque;
import com.microservico.estoqueservice.domain.repository.EstoqueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void deveCriarEstoqueComSkuNaoCadastrado() {
        Estoque novoEstoque = new Estoque();
        novoEstoque.setSku("sku123");
        novoEstoque.setQuantidade(10);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.empty());
        when(estoqueRepository.save(novoEstoque)).thenReturn(novoEstoque);

        Estoque resultado = estoqueService.criarEstoque(novoEstoque);

        assertNotNull(resultado);
        assertEquals("sku123", resultado.getSku());
        verify(estoqueRepository).findBySku("sku123");
        verify(estoqueRepository).save(novoEstoque);
    }

    @Test
    void deveLancarExcecaoAoCriarEstoqueComSkuJaCadastrado() {
        Estoque existente = new Estoque();
        existente.setSku("sku123");
        existente.setQuantidade(5);

        Estoque novo = new Estoque();
        novo.setSku("sku123");
        novo.setQuantidade(10);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(existente));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            estoqueService.criarEstoque(novo);
        });

        assertEquals("Estoque já cadastrado para o SKU: sku123", ex.getMessage());
        verify(estoqueRepository).findBySku("sku123");
        verify(estoqueRepository, never()).save(any());
    }

    @Test
    void deveAtualizarQuantidadeDeEstoque() {
        Estoque estoqueExistente = new Estoque();
        estoqueExistente.setSku("sku123");
        estoqueExistente.setQuantidade(5);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(estoqueExistente));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estoque atualizado = estoqueService.atualizarEstoque("sku123", 20);

        assertEquals(20, atualizado.getQuantidade());
        verify(estoqueRepository).findBySku("sku123");
        verify(estoqueRepository).save(estoqueExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarEstoqueInexistente() {
        when(estoqueRepository.findBySku("skuInexistente")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            estoqueService.atualizarEstoque("skuInexistente", 10);
        });

        assertEquals("Estoque não encontrado para o SKU: skuInexistente", ex.getMessage());
    }

    @Test
    void deveBaixarEstoqueComQuantidadeSuficiente() {
        Estoque estoque = new Estoque();
        estoque.setSku("sku123");
        estoque.setQuantidade(10);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estoque resultado = estoqueService.baixarEstoque("sku123", 5);

        assertEquals(5, resultado.getQuantidade());
        verify(estoqueRepository).save(estoque);
    }

    @Test
    void deveLancarExcecaoAoBaixarEstoqueInsuficiente() {
        Estoque estoque = new Estoque();
        estoque.setSku("sku123");
        estoque.setQuantidade(4);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(estoque));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            estoqueService.baixarEstoque("sku123", 10);
        });

        assertEquals("Estoque insuficiente para o SKU: sku123", ex.getMessage());
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Estoque estoque = new Estoque();
        estoque.setSku("sku123");
        estoque.setQuantidade(5);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estoque resultado = estoqueService.reporEstoque("sku123", 10);

        assertEquals(15, resultado.getQuantidade());
    }

    @Test
    void deveBuscarEstoquePorId() {
        Estoque estoque = new Estoque();
        estoque.setId("id123");
        estoque.setSku("sku123");
        estoque.setQuantidade(10);

        when(estoqueRepository.findById("id123")).thenReturn(Optional.of(estoque));

        Optional<Estoque> resultado = estoqueService.porId("id123");

        assertTrue(resultado.isPresent());
        assertEquals("sku123", resultado.get().getSku());
    }

    @Test
    void deveBuscarEstoquePorSku() {
        Estoque estoque = new Estoque();
        estoque.setSku("sku123");
        estoque.setQuantidade(10);

        when(estoqueRepository.findBySku("sku123")).thenReturn(Optional.of(estoque));

        Optional<Estoque> resultado = estoqueService.porSku("sku123");

        assertTrue(resultado.isPresent());
        assertEquals("sku123", resultado.get().getSku());
    }

    @Test
    void deveListarTodosEstoques() {
        Estoque e1 = new Estoque();
        e1.setSku("sku1");
        e1.setQuantidade(5);

        Estoque e2 = new Estoque();
        e2.setSku("sku2");
        e2.setQuantidade(15);

        when(estoqueRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Estoque> resultado = estoqueService.listarTodos();

        assertEquals(2, resultado.size());
        verify(estoqueRepository).findAll();
    }
}
