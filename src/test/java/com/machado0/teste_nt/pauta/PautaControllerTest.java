package com.machado0.teste_nt.pauta;

import com.machado0.teste_nt.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PautaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pautaController).build();
    }


    @Test
    public void criarPautaTest() throws Exception {
        PautaDTO pautaDTO = new PautaDTO(1L, "teste", "descrição", null);
        when(pautaService.criar(pautaDTO)).thenReturn(pautaDTO);

        mockMvc.perform(post("/pautas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(pautaDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listarTodosVotosTest() throws Exception {
        PautaDTO pautaDTO = new PautaDTO(1L, "teste", "descrição", null);
        ArrayList<PautaDTO> lista = new ArrayList<>();
        lista.add(pautaDTO);
        when(pautaService.listarTodas(any())).thenReturn(new PageImpl<>(lista));

        mockMvc.perform(get("/pautas")
                        .param("page", "0")
                        .param("size", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPorIdTest() throws Exception {
        when(pautaService.buscarPorId(anyLong())).thenReturn(new PautaDTO(1L, "teste", "descrição", null));

        mockMvc.perform(get("/pautas/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarPautaTest() throws Exception {
        PautaDTO pautaDTO = new PautaDTO(1L, "teste", "descrição", null);
        when(pautaService.atualizar(pautaDTO, 1L)).thenReturn(pautaDTO);

        mockMvc.perform(put("/pautas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(pautaDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void excluirPautaTest() throws Exception {
        mockMvc.perform(delete("/pautas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void abrirSessaoTest() throws Exception {
        mockMvc.perform(get("/pautas/abrir-sessao")
                        .param("pautaId", "1")
                        .param("tempoEncerramento", "2025-02-22T14:02:45.123-03:00"))
                .andExpect(status().isNoContent());
    }
}
