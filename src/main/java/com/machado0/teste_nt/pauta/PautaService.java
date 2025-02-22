package com.machado0.teste_nt.pauta;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PautaService {


    private final PautaRepository pautaRepository;

    public PautaDTO criar(PautaDTO pauta) {
        return PautaMapper.toDTO(pautaRepository.save(PautaMapper.toEntity(pauta)));
    }

    public PautaDTO buscarPorId(Long id) {
        return pautaRepository.findById(id)
                .map(PautaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
    }

    public Page<PautaDTO> listarTodas(Pageable pageable) {
        return pautaRepository.findAll(pageable)
                .map(PautaMapper::toDTO);
    }

    public void excluir(Long id) {
        pautaRepository.deleteById(id);
    }

    public PautaDTO atualizar(PautaDTO pauta, Long id) {
        if (!pautaRepository.existsById(id)) {
            throw new RuntimeException("Pauta não encontrada");
        }

        Pauta pautaEntity = PautaMapper.toEntity(pauta);
        pautaEntity.setId(id);

        return PautaMapper.toDTO(pautaRepository.save(pautaEntity));
    }

    public void abrirSessao(Long pautaId, OffsetDateTime tempoEncerramento) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
        pauta.setTempoEncerramento(tempoEncerramento);
        pautaRepository.save(pauta);
    }
}
