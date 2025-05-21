package com.microservico.estoqueservice.application.usecase;

import com.microservico.estoqueservice.domain.model.Estoque;

public interface AtualizarEstoque {
    Estoque atualizarEstoque(String sku, int novaQuantidade);
}
