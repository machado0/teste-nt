package com.machado0.teste_nt.voto;

import org.springframework.data.domain.Page;

public record ResultadoDTO(String resultado, Page<VotoDTO> votos) {
}
