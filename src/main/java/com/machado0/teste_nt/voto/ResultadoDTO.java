package com.machado0.teste_nt.voto;

import java.util.List;

public record ResultadoDTO(String resultado, List<VotoDTO> votos) {
}
