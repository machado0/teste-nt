package com.machado0.teste_nt.associado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class AssociadoServiceTest {

    private AssociadoService associadoService;

    @Autowired
    private AssociadoRepository associadoRepository;

    @BeforeEach
    public void setUp() {
        this.associadoService = new AssociadoService(associadoRepository);
    }

    @Test
    @DisplayName("Deve criar um associado")
    public void criarAssociadoTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-85"));
        AssociadoDTO associadoCriado = associadoService.criar(associado);

        assertEquals(associado.cpf(), associadoCriado.cpf());

        associadoService.excluir(associado.id());
    }

    @Test
    @DisplayName("Deve excluir um associado")
    public void excluirAssociadoTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-81"));

        assertNotNull(associadoService.buscarPorId(associado.id()));
        associadoService.excluir(associado.id());
        assertThrows(RuntimeException.class, () -> associadoService.buscarPorId(associado.id()));

    }

    @Test
    @DisplayName("Deve atualizar um associado")
    public void atualizarAssociadoTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-94"));
        AssociadoDTO associadoAtualizado = associadoService.atualizar(new AssociadoDTO(associado.id(), "888.888.888-77"), associado.id());

        assertEquals("88888888877", associadoAtualizado.cpf());

        associadoService.excluir(associado.id());
    }

    @Test
    @DisplayName("Deve buscar todos os associados")
    public void buscarTodosOsAssociadosTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-91"));
        AssociadoDTO associado2 = associadoService.criar(new AssociadoDTO(null, "888.888.888-93"));

        associado = associadoService.criar(associado);
        associado2 = associadoService.criar(associado2);

        assertEquals(2, associadoService.listarTodos(Pageable.unpaged()).getContent().size());
        associadoService.excluir(associado.id());
        associadoService.excluir(associado2.id());

    }
}
