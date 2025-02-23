package com.machado0.teste_nt.associado;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AssociadoServiceTest {

    @Autowired
    private AssociadoService associadoService;

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
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));

        assertDoesNotThrow(() -> associadoService.buscarPorId(associado.id()));
        associadoService.excluir(associado.id());
        assertThrows(RuntimeException.class, () -> associadoService.buscarPorId(associado.id()));

    }

    @Test
    @DisplayName("Deve atualizar um associado")
    public void atualizarAssociadoTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));
        AssociadoDTO associadoAtualizado = associadoService.atualizar(new AssociadoDTO(associado.id(), "888.888.888-99"), associado.id());

        assertEquals("888.888.888-99", associadoAtualizado.cpf());

        associadoService.excluir(associado.id());
    }

    @Test
    @DisplayName("Deve buscar todos os associados")
    public void buscarTodosOsAssociadosTest() {
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));
        AssociadoDTO associado2 = associadoService.criar(new AssociadoDTO(null, "888.888.888-87"));

        associado = associadoService.criar(associado);
        associado2 = associadoService.criar(associado2);

        assertEquals(2, associadoService.listarTodos(Pageable.unpaged()).getContent().size());
        associadoService.excluir(associado.id());
        associadoService.excluir(associado2.id());

    }
}
