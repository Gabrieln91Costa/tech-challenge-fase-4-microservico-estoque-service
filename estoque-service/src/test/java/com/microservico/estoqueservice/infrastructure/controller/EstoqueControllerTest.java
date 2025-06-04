package com.microservico.estoqueservice.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservico.estoqueservice.application.service.EstoqueService;
import com.microservico.estoqueservice.domain.exception.EstoqueNaoEncontradoException;
import com.microservico.estoqueservice.domain.model.Estoque;
import com.microservico.estoqueservice.infrastructure.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstoqueController.class)
@Import(GlobalExceptionHandler.class)
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstoqueService estoqueService;

    @Autowired
    private ObjectMapper objectMapper;

    private Estoque estoque;

    @BeforeEach
    void setup() {
        estoque = new Estoque();
        estoque.setSku("12345");
        estoque.setQuantidade(100);
    }

    @Test
    void deveCriarEstoque() throws Exception {
        when(estoqueService.criarEstoque(any(Estoque.class))).thenReturn(estoque);

        mockMvc.perform(post("/estoque")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estoque)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku", is("12345")))
                .andExpect(jsonPath("$.quantidade", is(100)));
    }

    @Test
    void deveBuscarEstoquePorSku() throws Exception {
        when(estoqueService.porSku("12345")).thenReturn(Optional.of(estoque));

        mockMvc.perform(get("/estoque/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku", is("12345")))
                .andExpect(jsonPath("$.quantidade", is(100)));
    }

    @Test
    void deveRetornarNotFoundSeSkuNaoExiste() throws Exception {
        String skuInexistente = "99999";
        when(estoqueService.porSku(skuInexistente))
                .thenThrow(new EstoqueNaoEncontradoException(skuInexistente));

        mockMvc.perform(get("/estoque/" + skuInexistente))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Estoque não encontrado para SKU: " + skuInexistente));
    }

    @Test
    void deveListarTodosEstoques() throws Exception {
        when(estoqueService.listarTodos()).thenReturn(List.of(estoque));

        mockMvc.perform(get("/estoque"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku", is("12345")))
                .andExpect(jsonPath("$[0].quantidade", is(100)));
    }

    @Test
    void deveBaixarEstoque() throws Exception {
        when(estoqueService.baixarEstoque(anyString(), anyInt())).thenReturn(estoque);

        mockMvc.perform(put("/estoque/12345/baixar")
                        .param("quantidade", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estoque.sku", is("12345")))
                .andExpect(jsonPath("$.mensagem", is("Baixa em estoque realizada com sucesso")));
    }

    @Test
    void deveReporEstoque() throws Exception {
        when(estoqueService.reporEstoque(anyString(), anyInt())).thenReturn(estoque);

        mockMvc.perform(put("/estoque/12345/repor")
                        .param("quantidade", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku", is("12345")))
                .andExpect(jsonPath("$.quantidade", is(100)));
    }
}
