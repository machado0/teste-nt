package com.machado0.teste_nt.config.kafka;

import com.machado0.teste_nt.pauta.PautaDTO;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.TimerTask;

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
        kafkaTemplateOrder.send("sessao-encerrada", pautaDTO.id().toString(), pautaDTO);
    }
}
