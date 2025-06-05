package com.example.desafio.v1.controller;

import com.example.desafio.V1.controller.UsuarioController;
import com.example.desafio.V1.dto.UsuarioDTO;
import com.example.desafio.core.service.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegistrar_Sucesso() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("João");
        dto.setEmail("joao@teste.com");
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha123");

        when(usuarioService.registrarUsuario(any(UsuarioDTO.class)))
                .thenReturn("Usuário registrado com sucesso.");

        mockMvc.perform(post("/v1/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário registrado com sucesso."));
    }

    @Test
    void testRegistrar_ErroValidacao() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("João");
        dto.setEmail("joao@teste.com");
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha123");
        when(usuarioService.registrarUsuario(any(UsuarioDTO.class)))
                .thenReturn("Erro: E-mail já em uso.");

        mockMvc.perform(post("/v1/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: E-mail já em uso."));
    }
    @Test
    void testRegistrar_ResultadoNulo() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("João");
        dto.setEmail("joao@teste.com");
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha123");

        when(usuarioService.registrarUsuario(any(UsuarioDTO.class)))
                .thenReturn(null); // Simula erro interno

        mockMvc.perform(post("/v1/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno: resultado nulo."));
    }
}
