package com.machado0.teste_nt.associado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public AssociadoDTO criar(AssociadoDTO associado) {
        Associado associadoEntity = AssociadoMapper.toEntity(associado);

        associadoEntity.setCpf(associadoEntity.getCpf().replace("-", ""));
        associadoEntity.setCpf(associadoEntity.getCpf().replace(".", ""));

        Status podeVotar;
        try {
//            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
            podeVotar = Status.ABLE_TO_VOTE;
        } catch (RuntimeException e) {
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
            throw new RuntimeException("Associado com este CPF não pode votar");
        }
    }

    public AssociadoDTO buscarPorId(Long id) {
        return associadoRepository.findById(id)
                .map(AssociadoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
    }

    public Page<AssociadoDTO> listarTodos(Pageable pageable) {
        return associadoRepository.findAll(pageable).map(AssociadoMapper::toDTO);
    }

    public void excluir(Long id) {
        associadoRepository.deleteById(id);
    }

    public AssociadoDTO atualizar(AssociadoDTO associado, Long id) {
        if (!associadoRepository.existsById(id)) {
            throw new RuntimeException("Associado não encontrado");
        }

        Associado associadoEntity = AssociadoMapper.toEntity(associado);
        associadoEntity.setId(id);

        Status podeVotar;
        try {
//            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
            podeVotar = Status.ABLE_TO_VOTE;
        } catch (RuntimeException e) {
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
            throw new RuntimeException("Associado com este CPF não pode votar");
        }
    }
}
