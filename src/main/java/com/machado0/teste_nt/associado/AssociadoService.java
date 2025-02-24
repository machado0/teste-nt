package com.machado0.teste_nt.associado;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public AssociadoDTO criar(AssociadoDTO associado) {
        Associado associadoEntity = AssociadoMapper.toEntity(associado);
        associadoEntity.setCpf(limparCpf(associadoEntity.getCpf()));

        Status podeVotar;
        try {
//            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
            podeVotar = Status.ABLE_TO_VOTE;
        } catch (RuntimeException e) {
            log.debug("Associado com este CPF {} tinha CPF inválido ao criar", associadoEntity.getCpf());
            throw new RuntimeException("CPF inválido");
        }
//        if (podeVotar.equals(Status.ABLE_TO_VOTE)) {
//            return AssociadoMapper.toDTO(associadoRepository.save(associadoEntity));
//        } else {
//            throw new RuntimeException("Associado com este CPF não pode votar");
//        }
        if (podeVotar.equals(Status.ABLE_TO_VOTE) && !associadoEntity.getCpf().endsWith("2")) {
            return AssociadoMapper.toDTO(associadoRepository.save(associadoEntity));
        } else {
            log.debug("Associado com este CPF {} não pode votar ao criar", associadoEntity.getCpf());
            throw new RuntimeException("Associado com este CPF não pode votar");
        }
    }

    public AssociadoDTO buscarPorId(Long id) {
        return associadoRepository.findById(id)
                .map(AssociadoMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Associado {} não encontrado ao buscar por id", id);
                    return new RuntimeException("Associado não encontrado");
                });
    }

    public Page<AssociadoDTO> listarTodos(Pageable pageable) {
        return associadoRepository.findAll(pageable).map(AssociadoMapper::toDTO);
    }

    public void excluir(Long id) {
        associadoRepository.deleteById(id);
    }

    public AssociadoDTO atualizar(AssociadoDTO associado, Long id) {
        if (!associadoRepository.existsById(id)) {
            log.error("Associado {} não encontrado ao atualizar", id);
            throw new RuntimeException("Associado não encontrado");
        }

        Associado associadoEntity = AssociadoMapper.toEntity(associado);
        associadoEntity.setId(id);
        associadoEntity.setCpf(limparCpf(associadoEntity.getCpf()));

        Status podeVotar;
        try {
//            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
            podeVotar = Status.ABLE_TO_VOTE;
        } catch (RuntimeException e) {
            log.debug("Associado com este CPF {} tinha CPF inválido ao atualizar", associadoEntity.getCpf());
            throw new RuntimeException("CPF inválido");
        }
//        if (podeVotar.equals(Status.ABLE_TO_VOTE)) {
//            return AssociadoMapper.toDTO(associadoRepository.save(associadoEntity));
//        } else {
//            throw new RuntimeException("Associado com este CPF não pode votar");
//        }
        if (podeVotar.equals(Status.ABLE_TO_VOTE) && !associadoEntity.getCpf().endsWith("2")) {
            return AssociadoMapper.toDTO(associadoRepository.save(associadoEntity));
        } else {
            log.debug("Associado com este CPF {} não pode votar ao atualizar", associadoEntity.getCpf());
            throw new RuntimeException("Associado com este CPF não pode votar");
        }
    }

    private String limparCpf(String cpf) {
        cpf = cpf.replace("-", "");
        cpf = cpf.replace(".", "");
        return cpf;
    }
}
