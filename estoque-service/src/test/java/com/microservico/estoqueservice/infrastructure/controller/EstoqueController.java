package com.microservico.estoqueservice.infrastructure.controller;

import com.microservico.estoqueservice.application.service.EstoqueService;
import com.microservico.estoqueservice.domain.exception.EstoqueNaoEncontradoException;
import com.microservico.estoqueservice.domain.model.Estoque;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; 

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<Estoque> criarEstoque(@RequestBody Estoque estoque) {
        Estoque estoqueCriado = estoqueService.criarEstoque(estoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueCriado);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Estoque> buscarEstoque(@PathVariable String sku) {
        Estoque estoque = estoqueService.porSku(sku)
                .orElseThrow(() -> new EstoqueNaoEncontradoException(sku));
        return ResponseEntity.ok(estoque);
    }

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @PutMapping("/{sku}/baixar")
    public ResponseEntity<?> baixarEstoque(@PathVariable String sku, @RequestParam int quantidade) {
        Estoque estoqueAtualizado = estoqueService.baixarEstoque(sku, quantidade);
        return ResponseEntity.ok(
                Map.of("estoque", estoqueAtualizado,
                       "mensagem", "Baixa em estoque realizada com sucesso")
        );
    }

    @PutMapping("/{sku}/repor")
    public ResponseEntity<Estoque> reporEstoque(@PathVariable String sku, @RequestParam int quantidade) {
        Estoque estoqueAtualizado = estoqueService.reporEstoque(sku, quantidade);
        return ResponseEntity.ok(estoqueAtualizado);
    }
}
