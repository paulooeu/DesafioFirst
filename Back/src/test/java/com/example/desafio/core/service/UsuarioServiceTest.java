package com.example.desafio.core.service;

import com.example.desafio.V1.dto.UsuarioDTO;
import com.example.desafio.core.integration.repository.UsuarioRepository;
import com.example.desafio.core.integration.util.SenhaUtils;
import com.example.desafio.core.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SenhaUtils senhaUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
    }
    @Test
    void testRegistrarUsuario_SenhasNaoCoincidem() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha456");

        String resultado = usuarioService.registrarUsuario(dto);

        assertEquals("Erro: Senhas não coincidem.", resultado);
    }

    @Test
    void testRegistrarUsuario_EmailJaEmUso() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha123");
        dto.setEmail("teste@teste.com");

        // Simula o caso onde o e-mail já está em uso
        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        String resultado = usuarioService.registrarUsuario(dto);

        assertEquals("Erro: E-mail já está em uso.", resultado);
    }

    @Test
    void testRegistrarUsuario_UsuarioRegistradoComSucesso() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("João");
        dto.setEmail("joao@teste.com");
        dto.setSenha("senha123");
        dto.setConfirmarSenha("senha123");

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getSenha())).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());


        // Simula o caso onde o e-mail não está em uso


        String resultado = usuarioService.registrarUsuario(dto);

        assertEquals("Usuário registrado com sucesso.", resultado);

        // Verifica se o método save foi chamado
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

}
