package com.microservico.estoqueservice.domain.repository;

import com.microservico.estoqueservice.domain.model.Estoque;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EstoqueRepository extends MongoRepository<Estoque, String> {

    Optional<Estoque> findBySku(String sku);
}
