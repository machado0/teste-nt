package com.machado0.teste_nt.associado;

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
public class AssociadoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssociadoService associadoService;

    @InjectMocks
    private AssociadoController associadoController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(associadoController).build();
    }

    @Test
    public void criarAssociadoTest() throws Exception {
        AssociadoDTO associadoDTO = new AssociadoDTO(1L, "999.999.999-99");
        when(associadoService.criar(associadoDTO))
                .thenReturn(associadoDTO);

        mockMvc.perform(post("/associados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(associadoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listarTodosAssociadosTest() throws Exception {
        AssociadoDTO associadoDTO = new AssociadoDTO(1L, "999.999.999-99");
        ArrayList<AssociadoDTO> lista = new ArrayList<>();
        lista.add(associadoDTO);
        when(associadoService.listarTodos(any()))
                .thenReturn(new PageImpl<>(lista));

        mockMvc.perform(get("/associados")
                        .param("page", "0")
                        .param("size", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPorIdTest() throws Exception {
        when(associadoService.buscarPorId(anyLong()))
                .thenReturn(new AssociadoDTO(1L, "999.999.999-99"));

        mockMvc.perform(get("/associados/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void atualizarAssociadoTest() throws Exception {
        AssociadoDTO associadoDTO = new AssociadoDTO(1L, "999.999.999-99");
        when(associadoService.atualizar(associadoDTO, 1L))
                .thenReturn(associadoDTO);

        mockMvc.perform(put("/associados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Utils.asJsonString(associadoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void excluirAssociadoTest() throws Exception {
        mockMvc.perform(delete("/associados/1"))
                .andExpect(status().isNoContent());
    }
}
