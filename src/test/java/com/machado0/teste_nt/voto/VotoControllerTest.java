package com.machado0.teste_nt.voto;

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
public class VotoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(votoController).build();
    }

    @Test
    public void criarVotoTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, 1L, 1L, true);
        when(votoService.criar(votoDTO)).thenReturn(votoDTO);

        mockMvc.perform(post("/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(votoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listarTodosVotosTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, 1L, 1L, true);
        ArrayList<VotoDTO> lista = new ArrayList<>();
        lista.add(votoDTO);
        when(votoService.listarTodos(any())).thenReturn(new PageImpl<>(lista));

        mockMvc.perform(get("/votos")
                        .param("page", "0")
                        .param("size", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPorIdTest() throws Exception {
        when(votoService.buscarPorId(anyLong())).thenReturn(new VotoDTO(1L, 1L, 1L, true));

        mockMvc.perform(get("/votos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarPautaTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, 1L, 1L, true);
        when(votoService.atualizar(votoDTO, 1L)).thenReturn(votoDTO);

        mockMvc.perform(put("/votos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(votoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void excluirPautaTest() throws Exception {
        mockMvc.perform(delete("/votos/1"))
                .andExpect(status().isNoContent());
    }
}
