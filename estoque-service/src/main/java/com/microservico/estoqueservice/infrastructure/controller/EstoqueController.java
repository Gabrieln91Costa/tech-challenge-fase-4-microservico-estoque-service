package com.microservico.estoqueservice.infrastructure.controller;

import com.microservico.estoqueservice.application.service.EstoqueService;
import com.microservico.estoqueservice.domain.model.Estoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // Método alterado para retornar mensagem junto com o estoque atualizado
    @PutMapping("/{sku}/baixar")
    public ResponseEntity<Map<String, Object>> baixarEstoque(@PathVariable String sku, @RequestParam Integer quantidade) {
        // 🖨️ Log para terminal
        System.out.println("📦 Requisição para baixar estoque recebida:");
        System.out.println("SKU: " + sku + " | Quantidade: " + quantidade);

        Estoque estoqueAtualizado = estoqueService.baixarEstoque(sku, quantidade);

        // 🖨️ Log após atualização
        System.out.println("✅ Baixa em estoque realizada com sucesso:");
        System.out.println(estoqueAtualizado);

        return ResponseEntity.ok(
                Map.of(
                        "estoque", estoqueAtualizado,
                        "mensagem", "Baixa em estoque realizada com sucesso"
                )
        );
    }


    @PutMapping("/{sku}/repor")
    public Estoque reporEstoque(@PathVariable String sku, @RequestParam Integer quantidade) {
        return estoqueService.reporEstoque(sku, quantidade);
    }
}
