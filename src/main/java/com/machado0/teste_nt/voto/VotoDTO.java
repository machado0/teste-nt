package com.machado0.teste_nt.voto;

import java.io.Serializable;

public record VotoDTO(Long id, Long pautaId, Long associadoId, boolean voto) implements Serializable {
}
