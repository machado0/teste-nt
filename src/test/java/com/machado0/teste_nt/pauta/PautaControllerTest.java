package com.machado0.teste_nt.pauta;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PautaControllerTest {

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    @Test
    public void criarPautaTest() {
        PautaDTO pautaDTO = new PautaDTO(1L, "titulo", "descricao", null);
        when(pautaService.criar(pautaDTO)).thenReturn(pautaDTO);

        ResponseEntity<PautaDTO> response = pautaController.criar(pautaDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(pautaDTO, response.getBody());
    }

    @Test
    public void listarTodasPautasTest() {
        PautaDTO pautaDTO = new PautaDTO(1L, "titulo", "descricao", null);
        when(pautaService.listarTodas(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(pautaDTO)));

        ResponseEntity<Page<PautaDTO>> response = pautaController.listarTodas(Pageable.unpaged());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(pautaDTO), response.getBody().getContent());
    }

    @Test
    public void buscarPorIdTest() {
        PautaDTO pautaDTO = new PautaDTO(1L, "titulo", "descricao", null);
        when(pautaService.buscarPorId(1L)).thenReturn(pautaDTO);

        ResponseEntity<PautaDTO> response = pautaController.buscarPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pautaDTO, response.getBody());
    }

    @Test
    public void atualizarPautaTest() {
        PautaDTO pautaDTO = new PautaDTO(1L, "titulo", "descricao", null);
        when(pautaService.atualizar(pautaDTO, 1L)).thenReturn(pautaDTO);

        ResponseEntity<PautaDTO> response = pautaController.atualizar(1L, pautaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pautaDTO, response.getBody());
    }

    @Test
    public void excluirPautaTest() {
        ResponseEntity<Void> response = pautaController.excluir(1L);

        assertEquals(204, response.getStatusCodeValue());
    }
}
