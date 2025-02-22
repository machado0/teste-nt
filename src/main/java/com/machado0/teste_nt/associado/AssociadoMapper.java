package com.machado0.teste_nt.associado;

import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class AssociadoMapper {

    public static AssociadoDTO toDTO(Associado associado) {
        if (associado == null) {
            return null;
        }
        return new AssociadoDTO(nonNull(associado.getId()) ? associado.getId() : null,
                associado.getCpf());
    }

    public static Associado toEntity(AssociadoDTO associadoDTO) {
        if (associadoDTO == null) {
            return null;
        }

        return Associado.builder()
                .id(nonNull(associadoDTO.id()) ? associadoDTO.id() : null)
                .cpf(associadoDTO.cpf())
                .build();
    }
}
