package com.machado0.teste_nt.pauta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Pauta> criar(@RequestBody Pauta pauta) {
        Pauta pautaCriada = pautaService.criar(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaCriada);
    }

    @GetMapping
    public ResponseEntity<Page<Pauta>> listarTodas(@RequestParam Pageable pageable) {
        Page<Pauta> pautas = pautaService.listarTodas(pageable);
        return ResponseEntity.ok(pautas);
    }

    //TODO transformar tudo em DTO

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);
        return ResponseEntity.ok(pauta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pauta> atualizar(@PathVariable Long id, @RequestBody Pauta pauta) {
        pauta.setId(id);
        Pauta pautaAtualizada = pautaService.atualizar(pauta);
        return ResponseEntity.ok(pautaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pautaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/abrirSessao")
    public ResponseEntity<Void> abrirSessao(@RequestParam Long pautaId, @RequestParam OffsetDateTime tempoEncerramento) {
        pautaService.abrirSessao(pautaId, tempoEncerramento);
        return ResponseEntity.noContent().build();
    }

}
