package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.associado.AssociadoDTO;
import com.machado0.teste_nt.associado.AssociadoRepository;
import com.machado0.teste_nt.associado.AssociadoService;
import com.machado0.teste_nt.associado.Status;
import com.machado0.teste_nt.config.IntegracaoUserInfo;
import com.machado0.teste_nt.pauta.PautaDTO;
import com.machado0.teste_nt.pauta.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
public class VotoServiceTest {

    @Autowired
    private VotoService votoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Mock
    private IntegracaoUserInfo integracaoUserInfoMock;

    private AssociadoService associadoServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.associadoServiceMock = new AssociadoService(associadoRepository, integracaoUserInfoMock);
    }

    @Test
    @DisplayName("Deve criar um voto")
    public void criarVotoTest() {
        when(integracaoUserInfoMock.verificarCpf("888.888.888-85")).thenReturn(Status.ABLE_TO_VOTE.toString());
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteCriarVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoServiceMock.criar(new AssociadoDTO(null, "888.888.888-85"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);

        VotoDTO votoCriado = votoService.criar(voto);

        assertEquals(pauta.id(), votoCriado.pautaId());
        assertEquals(associado.id(), votoCriado.associadoId());
        assertTrue(votoCriado.voto());

        votoService.excluir(votoCriado.id());
        associadoServiceMock.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve excluir um voto")
    public void excluirVotoTest() {
        when(integracaoUserInfoMock.verificarCpf("888.888.888-88")).thenReturn(Status.ABLE_TO_VOTE.toString());
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteExcluirVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoServiceMock.criar(new AssociadoDTO(null, "888.888.888-88"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);
        voto = votoService.criar(voto);

        VotoDTO finalVoto = voto;
        assertNotNull(votoService.buscarPorId(finalVoto.id()));
        votoService.excluir(voto.id());
        assertThrows(RuntimeException.class, () -> votoService.buscarPorId(finalVoto.id()));

        associadoServiceMock.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve atualizar um voto")
    public void atualizarVotoTest() {
        when(integracaoUserInfoMock.verificarCpf("888.888.888-88")).thenReturn(Status.ABLE_TO_VOTE.toString());
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteAtualizarVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoServiceMock.criar(new AssociadoDTO(null, "888.888.888-88"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), true);
        voto = votoService.criar(voto);

        VotoDTO pautaAtualizada = votoService.criar(new VotoDTO(voto.id(), pauta.id(), associado.id(), false));

        assertFalse(pautaAtualizada.voto());

        votoService.excluir(pautaAtualizada.id());
        associadoServiceMock.excluir(associado.id());
        pautaService.excluir(pauta.id());
    }

    @Test
    @DisplayName("Deve buscar todos os votos")
    public void buscarTodosOsVotosTest() {
        when(integracaoUserInfoMock.verificarCpf("888.888.888-88")).thenReturn(Status.ABLE_TO_VOTE.toString());
        when(integracaoUserInfoMock.verificarCpf("888.888.888-87")).thenReturn(Status.ABLE_TO_VOTE.toString());
        PautaDTO pauta = pautaService.criar(new PautaDTO(null, "tituloTesteBuscarTodosVoto", "descricao", OffsetDateTime.now().plusMinutes(5)));
        AssociadoDTO associado = associadoServiceMock.criar(new AssociadoDTO(null, "888.888.888-88"));
        AssociadoDTO associado2 = associadoServiceMock.criar(new AssociadoDTO(null, "888.888.888-87"));

        VotoDTO voto = new VotoDTO(null, pauta.id(), associado.id(), false);
        VotoDTO voto2 = new VotoDTO(null, pauta.id(), associado2.id(), true);
        voto = votoService.criar(voto);
        voto2 = votoService.criar(voto2);

        assertEquals(2, votoService.listarTodos(Pageable.unpaged()).getContent().size());
        votoService.excluir(voto.id());
        votoService.excluir(voto2.id());
        associadoServiceMock.excluir(associado.id());
        associadoServiceMock.excluir(associado2.id());
        pautaService.excluir(pauta.id());
    }
}
