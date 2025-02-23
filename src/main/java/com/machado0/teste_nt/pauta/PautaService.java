package com.machado0.teste_nt.pauta;

import com.machado0.teste_nt.config.kafka.KafkaProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Timer;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class PautaService {

    @Autowired
    private final KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    private final PautaRepository pautaRepository;

    public PautaService(KafkaProducerConfig kafkaProducerConfig,
                        PautaRepository pautaRepository) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.pautaRepository = pautaRepository;
    }

    public PautaDTO criar(PautaDTO pauta) {
        PautaDTO pautaDTO = PautaMapper.toDTO(pautaRepository.save(PautaMapper.toEntity(pauta)));
        verificarExisteTempoEncerramento(pautaDTO);
        return pautaDTO;
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

        PautaDTO pautaSalva = PautaMapper.toDTO(pautaRepository.save(pautaEntity));
        verificarExisteTempoEncerramento(pautaSalva);
        return pautaSalva;
    }

    public void abrirSessao(Long pautaId, OffsetDateTime tempoEncerramento) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
        pauta.setTempoEncerramento(tempoEncerramento);
        Pauta pautaSalva = pautaRepository.save(pauta);
        enviarMensagemEncerramentoSessao(PautaMapper.toDTO(pautaSalva));
    }

    private void verificarExisteTempoEncerramento(PautaDTO pautaDTO) {
        if (nonNull(pautaDTO.tempoEncerramento()) &&
                OffsetDateTime.now().isAfter(pautaDTO.tempoEncerramento())) {
            enviarMensagemEncerramentoSessao(pautaDTO);
        }
    }

    public void enviarMensagemEncerramentoSessao(PautaDTO pautaDTO) {
        new Timer().schedule(new EncerramentoSessaoTask(pautaDTO, kafkaProducerConfig.pautaDtoKafkaTemplate()), Date.from(pautaDTO.tempoEncerramento().toInstant()));
    }

}
