package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.associado.AssociadoDTO;
import com.machado0.teste_nt.associado.AssociadoService;
import com.machado0.teste_nt.pauta.PautaDTO;
import com.machado0.teste_nt.pauta.PautaService;
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

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class VotoServiceTest {

    @Autowired
    private VotoService votoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoService associadoService;

    @Test
    @DisplayName("Deve criar um voto")
    public void criarVotoTest() {
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteCriarVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-85"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);

        VotoDTO votoCriado = votoService.criar(voto);

        assertEquals(pauta.id(), votoCriado.pautaId());
        assertEquals(associado.id(), votoCriado.associadoId());
        assertTrue(votoCriado.voto());

        votoService.excluir(votoCriado.id());
        associadoService.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve excluir um voto")
    public void excluirVotoTest() {
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteExcluirVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);
        voto = votoService.criar(voto);

        VotoDTO finalVoto = voto;
        assertDoesNotThrow(() -> votoService.buscarPorId(finalVoto.id()));
        votoService.excluir(voto.id());
        assertThrows(RuntimeException.class, () -> votoService.buscarPorId(finalVoto.id()));

        associadoService.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve atualizar um voto")
    public void atualizarVotoTest() {
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteAtualizarVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);
        voto = votoService.criar(voto);

        VotoDTO pautaAtualizada = votoService.criar(new VotoDTO(voto.id(), pauta.id(), associado.id(), false));

        assertFalse(pautaAtualizada.voto());

        votoService.excluir(pautaAtualizada.id());
        associadoService.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve buscar todos os votos")
    public void buscarTodosOsVotosTest() {
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteBuscarTodosVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoService.criar(new AssociadoDTO(null, "888.888.888-88"));
        AssociadoDTO associado2 = associadoService.criar(new AssociadoDTO(null, "888.888.888-87"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), false);
        VotoDTO voto2 = new VotoDTO(null, pauta.id(), associado2.id(), true);
        voto = votoService.criar(voto);
        voto2 = votoService.criar(voto2);

        assertEquals(2, votoService.listarTodos(Pageable.unpaged()).getContent().size());
        votoService.excluir(voto.id());
        votoService.excluir(voto2.id());
        associadoService.excluir(associado.id());
        associadoService.excluir(associado2.id());
        pautaService.excluir(pauta.id());

    }
}
