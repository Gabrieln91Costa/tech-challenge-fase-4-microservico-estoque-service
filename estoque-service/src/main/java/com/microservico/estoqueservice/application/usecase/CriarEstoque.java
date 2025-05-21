package com.microservico.estoqueservice.application.usecase;

import com.microservico.estoqueservice.domain.model.Estoque;

public interface CriarEstoque {
    Estoque criarEstoque(Estoque estoque);
}
