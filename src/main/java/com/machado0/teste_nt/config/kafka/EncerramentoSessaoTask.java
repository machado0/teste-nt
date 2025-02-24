package com.machado0.teste_nt.config.kafka;

import com.machado0.teste_nt.pauta.PautaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.TimerTask;

@Slf4j
public class EncerramentoSessaoTask extends TimerTask {

    private final PautaDTO pautaDTO;
    private final KafkaTemplate<String, PautaDTO> kafkaTemplateOrder;

    public EncerramentoSessaoTask(PautaDTO pautaDTO,
                                  KafkaTemplate<String, PautaDTO> kafkaTemplateOrder) {
        this.pautaDTO = pautaDTO;
        this.kafkaTemplateOrder = kafkaTemplateOrder;
    }

    @Override
    public void run() {
        log.debug("Encerrando sessao da pauta {}", pautaDTO.id());
        kafkaTemplateOrder.send("sessao-encerrada", pautaDTO.id().toString(), pautaDTO);
    }
}
