package com.machado0.teste_nt.associado;

import com.machado0.teste_nt.config.IntegracaoUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final IntegracaoUserInfo integracaoUserInfo;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository, IntegracaoUserInfo integracaoUserInfo) {
        this.associadoRepository = associadoRepository;
        this.integracaoUserInfo = integracaoUserInfo;
    }

    public AssociadoDTO criar(AssociadoDTO associado) {
        Associado associadoEntity = AssociadoMapper.toEntity(associado);
        String podeVotar;
        try {
            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
        } catch (RuntimeException e) {
            throw new RuntimeException("CPF inválido");
        }
        if (podeVotar.equals(Status.ABLE_TO_VOTE.toString())) {
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

        String podeVotar;
        try {
            podeVotar = integracaoUserInfo.verificarCpf(associadoEntity.getCpf());
        } catch (RuntimeException e) {
            throw new RuntimeException("CPF inválido");
        }

        if (podeVotar.equals(Status.ABLE_TO_VOTE.toString())) {
            return AssociadoMapper.toDTO(associadoRepository.save(associadoEntity));
        } else {
            throw new RuntimeException("Associado com este CPF não pode votar");
        }
    }
}
