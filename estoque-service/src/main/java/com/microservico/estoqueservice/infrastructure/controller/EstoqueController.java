package com.microservico.estoqueservice.infrastructure.controller;

import com.microservico.estoqueservice.application.service.EstoqueService;
import com.microservico.estoqueservice.domain.model.Estoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estoque criarEstoque(@RequestBody Estoque estoque) {
        return estoqueService.criarEstoque(estoque);
    }

    @GetMapping("/{sku}")
    public Estoque buscarEstoque(@PathVariable String sku) {
        return estoqueService.porSku(sku)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para SKU: " + sku));
    }

    @GetMapping
    public List<Estoque> listarEstoque() {
        return estoqueService.listarTodos();
    }

    @PutMapping("/{sku}/baixar")
    public Estoque baixarEstoque(@PathVariable String sku, @RequestParam Integer quantidade) {
        return estoqueService.baixarEstoque(sku, quantidade);
    }

    @PutMapping("/{sku}/repor")
    public Estoque reporEstoque(@PathVariable String sku, @RequestParam Integer quantidade) {
        return estoqueService.reporEstoque(sku, quantidade);
    }
}
