package com.machado0.teste_nt.pauta;

import com.machado0.teste_nt.util.PageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaDTO> criar(@RequestBody PautaDTO pauta) {
        PautaDTO pautaCriada = pautaService.criar(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaCriada);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PautaDTO>> listarTodas(@RequestParam(defaultValue = "0", required = false) int page,
                                                              @RequestParam(defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok((new PageResponse<>(pautaService.listarTodas(PageRequest.of(page, size)))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> buscarPorId(@PathVariable Long id) {
        PautaDTO pauta = pautaService.buscarPorId(id);
        return ResponseEntity.ok(pauta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDTO> atualizar(@PathVariable Long id,
                                              @RequestBody PautaDTO pauta) {
        PautaDTO pautaAtualizada = pautaService.atualizar(pauta, id);
        return ResponseEntity.ok(pautaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pautaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/abrir-sessao")
    public ResponseEntity<Void> abrirSessao(@RequestParam Long pautaId,
                                            @RequestParam OffsetDateTime tempoEncerramento) {
        pautaService.abrirSessao(pautaId, tempoEncerramento);
        return ResponseEntity.noContent().build();
    }

}
