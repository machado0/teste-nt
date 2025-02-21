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

    public Pauta criar(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public Pauta buscarPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
    }

    public Page<Pauta> listarTodas(Pageable pageable) {
        return pautaRepository.findAll(pageable);
    }

    public void excluir(Long id) {
        pautaRepository.deleteById(id);
    }

    public Pauta atualizar(Pauta pauta) {
        if (!pautaRepository.existsById(pauta.getId())) {
            throw new RuntimeException("Pauta não encontrada");
        }


        return pautaRepository.save(pauta);
    }

    public void abrirSessao(Long pautaId, OffsetDateTime tempoEncerramento) {
        Pauta pauta = this.buscarPorId(pautaId);
        pauta.setTempoEncerramento(tempoEncerramento);
        this.atualizar(pauta);
    }
}
