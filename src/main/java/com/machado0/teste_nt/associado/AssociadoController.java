package com.machado0.teste_nt.associado;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping
    public ResponseEntity<AssociadoDTO> criar(@RequestBody AssociadoDTO associado) {
        AssociadoDTO associadoCriado = associadoService.criar(associado);
        return ResponseEntity.status(HttpStatus.CREATED).body(associadoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<AssociadoDTO>> listarTodos(@PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable ) {
        Page<AssociadoDTO> associados = associadoService.listarTodos(pageable);
        return ResponseEntity.ok(associados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> buscarPorId(@PathVariable Long id) {
        AssociadoDTO associado = associadoService.buscarPorId(id);
        return ResponseEntity.ok(associado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoDTO> atualizar(@PathVariable Long id,
                                                  @RequestBody AssociadoDTO associado) {
        AssociadoDTO associadoAtualizado = associadoService.atualizar(associado, id);
        return ResponseEntity.ok(associadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        associadoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
