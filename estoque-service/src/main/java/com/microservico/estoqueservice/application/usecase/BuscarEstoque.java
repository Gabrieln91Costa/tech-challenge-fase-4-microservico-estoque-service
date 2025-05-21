package com.microservico.estoqueservice.application.usecase;

import com.microservico.estoqueservice.domain.model.Estoque;

import java.util.Optional;

public interface BuscarEstoque {
    Optional<Estoque> porId(String id);  // Alterando de Long para String
    Optional<Estoque> porSku(String sku);
}
