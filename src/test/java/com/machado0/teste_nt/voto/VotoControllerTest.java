package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class VotoControllerTest {

    private MockMvc mockMvc;

    private VotoController votoController;

    @Mock
    private VotoService votoServiceMock;

    @BeforeEach
    public void setup() {
        votoController = new VotoController(votoServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(votoController).build();
    }

    @Test
    public void criarVotoTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, 1L, 1L, true);
        when(votoServiceMock.criar(votoDTO)).thenReturn(votoDTO);

        mockMvc.perform(post("/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(votoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listarTodosVotosTest() throws Exception {
        ArrayList<VotoDTO> lista = new ArrayList<>();
        lista.add(new VotoDTO(1L, 1L, 1L, true));
        Page<VotoDTO> page = new PageImpl<>(lista);

        when(votoServiceMock.listarTodos(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/votos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));
    }


    @Test
    public void buscarPorIdTest() throws Exception {
        when(votoServiceMock.buscarPorId(anyLong())).thenReturn(new VotoDTO(1L, 1L, 1L, true));

        mockMvc.perform(get("/votos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarPautaTest() throws Exception {
        VotoDTO votoDTO = new VotoDTO(1L, 1L, 1L, true);
        when(votoServiceMock.atualizar(any(), anyLong())).thenReturn(votoDTO);

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
