package com.machado0.teste_nt.voto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }
    //TODO transformar tudo em DTO
    @PostMapping
    public ResponseEntity<Voto> criar(@RequestBody Voto voto) {
        Voto votoCriado = votoService.criar(voto);
        return ResponseEntity.status(HttpStatus.CREATED).body(votoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<Voto>> listarTodos(@RequestParam Pageable pageable) {
        Page<Voto> votos = votoService.listarTodos(pageable);
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voto> buscarPorId(@PathVariable Long id) {
        Voto voto = votoService.buscarPorId(id);
        return ResponseEntity.ok(voto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voto> atualizar(@PathVariable Long id, @RequestBody Voto voto) {
        voto.setId(id);
        Voto votoAtualizado = votoService.atualizar(voto);
        return ResponseEntity.ok(votoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        votoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
