package com.machado0.teste_nt.pauta;

import com.machado0.teste_nt.config.kafka.KafkaProducerConfig;
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
class PautaServiceTest {

    private PautaService pautaService;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    @BeforeEach
    public void setUp() {
        this.pautaService = new PautaService(kafkaProducerConfig, pautaRepository);
    }

    @Test
    @DisplayName("Deve criar uma pauta")
    public void criarPautaTest() {
        PautaDTO pauta = new PautaDTO(null, "tituloTesteCriar", "descricao", null);

        PautaDTO pautaCriada = pautaService.criar(pauta);

        assertEquals("tituloTesteCriar", pautaCriada.titulo());
        assertEquals("descricao", pautaCriada.descricao());

        pautaService.excluir(pautaCriada.id());
    }

    @Test
    @DisplayName("Deve excluir uma pauta")
    public void excluirPautaTest() {
        PautaDTO pauta = new PautaDTO(null, "tituloTesteExcluir", "descricao", null);
        pauta = pautaService.criar(pauta);

        PautaDTO finalPauta = pauta;
        assertNotNull(pautaService.buscarPorId(finalPauta.id()));
        pautaService.excluir(pauta.id());
        assertThrows(RuntimeException.class, () -> pautaService.buscarPorId(finalPauta.id()));
    }

    @Test
    @DisplayName("Deve atualizar uma pauta")
    public void atualizarPautaTest() {
        PautaDTO pauta = new PautaDTO(null, "tituloTesteAtualizar", "descricao", null);
        pauta = pautaService.criar(pauta);

        PautaDTO pautaAtualizada = pautaService.criar(new PautaDTO(pauta.id(), "tituloAtualizado", "descricao", null));

        assertEquals("tituloAtualizado", pautaAtualizada.titulo());
        pautaService.excluir(pautaAtualizada.id());
    }

    @Test
    @DisplayName("Deve buscar todas as pautas")
    public void buscarTodasAsPautaTest() {
        PautaDTO pauta1 = new PautaDTO(null, "tituloTesteBuscar1", "descricao", null);
        PautaDTO pauta2 = new PautaDTO(null, "tituloTesteBuscar2", "descricao", null);
        pauta1 = pautaService.criar(pauta1);
        pauta2 = pautaService.criar(pauta2);

        assertEquals(2, pautaService.listarTodas(Pageable.unpaged()).getContent().size());
        pautaService.excluir(pauta1.id());
        pautaService.excluir(pauta2.id());
    }
}
