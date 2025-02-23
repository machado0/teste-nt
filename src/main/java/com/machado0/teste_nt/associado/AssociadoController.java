package com.machado0.teste_nt.associado;

import com.machado0.teste_nt.util.PageResponse;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<PageResponse<AssociadoDTO>> listarTodos(@RequestParam(defaultValue = "0", required = false) int page,
                                                                  @RequestParam(defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok((new PageResponse<>(associadoService.listarTodos(PageRequest.of(page, size)))));
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
