package com.microservico.estoqueservice.application.service;

import com.microservico.estoqueservice.application.usecase.CriarEstoque;
import com.microservico.estoqueservice.application.usecase.BuscarEstoque;
import com.microservico.estoqueservice.application.usecase.AtualizarEstoque;
import com.microservico.estoqueservice.domain.model.Estoque;
import com.microservico.estoqueservice.domain.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService implements CriarEstoque, BuscarEstoque, AtualizarEstoque {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Override
    public Estoque criarEstoque(Estoque estoque) {
        Optional<Estoque> existente = estoqueRepository.findBySku(estoque.getSku());
        if (existente.isPresent()) {
            throw new RuntimeException("Estoque já cadastrado para o SKU: " + estoque.getSku());
        }
        return estoqueRepository.save(estoque);
    }

    @Override
    public Optional<Estoque> porId(String id) {  // ID do MongoDB é String
        return estoqueRepository.findById(id);
    }

    @Override
    public Optional<Estoque> porSku(String sku) {
        return estoqueRepository.findBySku(sku);
    }

    @Override
    public Estoque atualizarEstoque(String sku, int novaQuantidade) {
        Estoque estoque = estoqueRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o SKU: " + sku));
        estoque.setQuantidade(novaQuantidade);
        return estoqueRepository.save(estoque);
    }

    public Estoque baixarEstoque(String sku, int quantidade) {
        Estoque estoque = estoqueRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o SKU: " + sku));

        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para o SKU: " + sku);
        }

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        return estoqueRepository.save(estoque);
    }

    public Estoque reporEstoque(String sku, int quantidade) {
        Estoque estoque = estoqueRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o SKU: " + sku));

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        return estoqueRepository.save(estoque);
    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }
}
