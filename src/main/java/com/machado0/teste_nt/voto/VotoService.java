package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.pauta.PautaDTO;
import com.machado0.teste_nt.pauta.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static java.util.Objects.nonNull;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final PautaService pautaService;

    @Autowired
    public VotoService(VotoRepository votoRepository,
                       PautaService pautaService) {
        this.votoRepository = votoRepository;
        this.pautaService = pautaService;
    }

    public VotoDTO criar(VotoDTO voto) {
        PautaDTO pauta = pautaService.buscarPorId(voto.pautaId());
        if (nonNull(pauta.tempoEncerramento()) &&
                OffsetDateTime.now().isBefore(pauta.tempoEncerramento())) {
            return VotoMapper.toDTO(votoRepository.save(VotoMapper.toEntity(voto)));
        } else {
            throw new RuntimeException("Sessão encerrada");
        }

    }

    public VotoDTO buscarPorId(Long id) {
        return votoRepository.findById(id)
                .map(VotoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Voto não encontrado"));
    }

    public Page<VotoDTO> listarTodos(Pageable pageable) {
        return votoRepository.findAll(pageable)
                .map(VotoMapper::toDTO);
    }

    public void excluir(Long id) {
        votoRepository.deleteById(id);
    }

    public VotoDTO atualizar(VotoDTO voto, Long id) {
        if (!votoRepository.existsById(id)) {
            throw new RuntimeException("Voto não encontrado");
        }

        Voto votoEntity = VotoMapper.toEntity(voto);
        votoEntity.setId(id);

        return VotoMapper.toDTO(votoRepository.save(votoEntity));
    }

    public ResultadoDTO listarResultados(Long pautaId, Pageable pageable) {
        ResultadoDTO resultadoDTO;
        int votosPositivos = 0;
        Page<VotoDTO> votos = listarVotosPorPauta(pautaId, pageable);
        if (votos.getContent().isEmpty()) {
            return new ResultadoDTO("Sem votos", votos);
        }
        for (VotoDTO voto : votos) {
            if (voto.voto()) {
                votosPositivos += 1;
            }
        }
        if (votosPositivos > votos.getContent().size() / 2) {
            return new ResultadoDTO("Pauta aprovada", votos);
        } else {
            return new ResultadoDTO("Pauta reprovada", votos);
        }
    }

    private Page<VotoDTO> listarVotosPorPauta(Long pautaId, Pageable pageable) {
        return votoRepository.findByPautaId(pautaId, pageable)
                .map(VotoMapper::toDTO);
    }
}
