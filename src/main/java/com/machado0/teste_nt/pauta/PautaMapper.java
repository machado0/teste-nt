package com.machado0.teste_nt.pauta;

import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class PautaMapper {

    public static PautaDTO toDTO(Pauta pauta) {
        if (pauta == null) {
            return null;
        }
        return new PautaDTO(
                nonNull(pauta.getId()) ? pauta.getId() : null,
                pauta.getTitulo(),
                pauta.getDescricao(),
                pauta.getTempoEncerramento()
        );
    }

    public static Pauta toEntity(PautaDTO pautaDTO) {
        if (pautaDTO == null) {
            return null;
        }
        return Pauta.builder()
                .id(nonNull(pautaDTO.id()) ? pautaDTO.id() : null)
                .titulo(pautaDTO.titulo())
                .descricao(pautaDTO.descricao())
                .tempoEncerramento(pautaDTO.tempoEncerramento())
                .build();
    }
}

