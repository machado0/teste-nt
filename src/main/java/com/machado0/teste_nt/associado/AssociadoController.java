package com.machado0.teste_nt.associado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    //TODO transformar tudo em DTO

    @PostMapping
    public ResponseEntity<Associado> criar(@RequestBody Associado associado) {
        Associado associadoCriado = associadoService.criar(associado);
        return ResponseEntity.status(HttpStatus.CREATED).body(associadoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<Associado>> listarTodos(@RequestParam Pageable pageable) {
        Page<Associado> associados = associadoService.listarTodos(pageable);
        return ResponseEntity.ok(associados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associado> buscarPorId(@PathVariable Long id) {
        Associado associado = associadoService.buscarPorId(id);
        return ResponseEntity.ok(associado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Associado> atualizar(@PathVariable Long id, @RequestBody Associado associado) {
        associado.setId(id);
        Associado associadoAtualizado = associadoService.atualizar(associado);
        return ResponseEntity.ok(associadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        associadoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
