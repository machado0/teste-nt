package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.associado.Associado;
import com.machado0.teste_nt.pauta.Pauta;
import org.springframework.stereotype.Component;

@Component
public class VotoMapper {

    public static VotoDTO toDTO(Voto voto) {
        if (voto == null) {
            return null;
        }
        return new VotoDTO(
                voto.getId(),
                voto.getPauta().getId(),
                voto.getAssociado().getId(),
                voto.isVoto()
        );
    }

    public static Voto toEntity(VotoDTO votoDTO) {
        if (votoDTO == null) {
            return null;
        }
        return Voto.builder()
                .id(votoDTO.id())
                .associado(Associado.builder()
                        .id(votoDTO.associadoId())
                        .build())
                .pauta(Pauta.builder()
                        .id(votoDTO.pautaId())
                        .build())
                .voto(votoDTO.voto())
                .build();
    }
}

