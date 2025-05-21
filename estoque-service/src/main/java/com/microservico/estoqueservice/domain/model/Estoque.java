package com.microservico.estoqueservice.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "estoque") // Especifica a coleção no MongoDB
public class Estoque {

    @Id
    private String id; // Pode ser Long, se você quiser usar números como IDs, mas por padrão é String no MongoDB

    private String sku; // SKU do produto — serve para relacionar

    private Integer quantidade; // Quantidade do produto em estoque
}
