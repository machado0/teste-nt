package com.machado0.teste_nt.associado;

import com.machado0.teste_nt.config.IntegracaoUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class AssociadoServiceTest {

    private AssociadoService associadoService;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Mock
    private IntegracaoUserInfo integracaoUserInfoMock;

    @BeforeEach
    public void setUp() {
        this.associadoService = new AssociadoService(associadoRepository, integracaoUserInfoMock);
    }

    @Test
    @DisplayName("Deve criar um associado")
    public void criarAssociadoTest() {
        when(integracaoUserInfoMock.verificarCpf(anyString())).thenReturn(Status.ABLE_TO_VOTE.toString());
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-82"));
        AssociadoDTO associadoCriado = associadoService.criar(associado);

        assertEquals(associado.cpf(), associadoCriado.cpf());

        associadoService.excluir(associado.id());
    }

    @Test
    @DisplayName("Deve excluir um associado")
    public void excluirAssociadoTest() {
        when(integracaoUserInfoMock.verificarCpf(anyString())).thenReturn(Status.ABLE_TO_VOTE.toString());
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-81"));

        assertNotNull(associadoService.buscarPorId(associado.id()));
        associadoService.excluir(associado.id());
        assertThrows(RuntimeException.class, () -> associadoService.buscarPorId(associado.id()));

    }

    @Test
    @DisplayName("Deve atualizar um associado")
    public void atualizarAssociadoTest() {
        when(integracaoUserInfoMock.verificarCpf(anyString())).thenReturn(Status.ABLE_TO_VOTE.toString());
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-92"));
        AssociadoDTO associadoAtualizado = associadoService.atualizar(new AssociadoDTO(associado.id(), "888.888.888-77"), associado.id());

        assertEquals("888.888.888-77", associadoAtualizado.cpf());

        associadoService.excluir(associado.id());
    }

    @Test
    @DisplayName("Deve buscar todos os associados")
    public void buscarTodosOsAssociadosTest() {
        when(integracaoUserInfoMock.verificarCpf(anyString())).thenReturn(Status.ABLE_TO_VOTE.toString());
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-91"));
        AssociadoDTO associado2 = associadoService.criar(new AssociadoDTO(null, "888.888.888-93"));

        associado = associadoService.criar(associado);
        associado2 = associadoService.criar(associado2);

        assertEquals(2, associadoService.listarTodos(Pageable.unpaged()).getContent().size());
        associadoService.excluir(associado.id());
        associadoService.excluir(associado2.id());

    }
}
