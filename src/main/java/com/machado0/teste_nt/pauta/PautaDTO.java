package com.machado0.teste_nt.pauta;

import java.time.OffsetDateTime;

public record PautaDTO(Long id, String titulo, String descricao, OffsetDateTime tempoEncerramento) {
}
