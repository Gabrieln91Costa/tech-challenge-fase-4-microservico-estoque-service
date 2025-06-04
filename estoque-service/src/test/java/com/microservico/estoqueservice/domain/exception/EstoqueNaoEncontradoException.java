package com.microservico.estoqueservice.domain.exception;

public class EstoqueNaoEncontradoException extends RuntimeException {
    public EstoqueNaoEncontradoException(String sku) {
        super("Estoque não encontrado para SKU: " + sku);
    }
}
