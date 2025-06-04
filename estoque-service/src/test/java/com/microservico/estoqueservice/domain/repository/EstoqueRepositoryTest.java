package com.microservico.estoqueservice.domain.repository;

import com.microservico.estoqueservice.domain.model.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class EstoqueRepositoryTest {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @BeforeEach
    void limparBanco() {
        estoqueRepository.deleteAll(); // limpa antes de cada teste
    }

    @Test
    @DisplayName("Deve salvar e encontrar Estoque por SKU")
    void deveSalvarEEncontrarPorSku() {
        // cria estoque de exemplo
        Estoque estoque = new Estoque();
        estoque.setSku("SKU-123");
        estoque.setQuantidade(50);

        // salva estoque
        estoqueRepository.save(estoque);

        // busca pelo SKU
        Optional<Estoque> encontrado = estoqueRepository.findBySku("SKU-123");

        // valida resultados
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getQuantidade()).isEqualTo(50);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar SKU inexistente")
    void deveRetornarVazioSeSkuNaoExistir() {
        Optional<Estoque> encontrado = estoqueRepository.findBySku("INEXISTENTE-SKU");
        assertThat(encontrado).isNotPresent();
    }
}
