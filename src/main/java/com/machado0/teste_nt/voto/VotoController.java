package com.machado0.teste_nt.voto;

import com.machado0.teste_nt.util.PageResponse;
import org.springframework.data.domain.PageRequest;
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

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody VotoDTO voto) {
        VotoDTO votoCriado;
        try {
            votoCriado = votoService.criar(voto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sessão encerrada");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(votoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(votoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<VotoDTO>> listarTodos(@RequestParam(defaultValue = "0", required = false) int page,
                                                             @RequestParam(defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok((new PageResponse<>(votoService.listarTodos(PageRequest.of(page, size)))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotoDTO> atualizar(@PathVariable Long id,
                                             @RequestBody VotoDTO voto) {
        VotoDTO votoAtualizado = votoService.atualizar(voto, id);
        return ResponseEntity.ok(votoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        votoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resultados/{pautaId}")
    public ResponseEntity<ResultadoDTO> listarResultados(@PathVariable Long pautaId,
                                                         @RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(votoService.listarResultados(pautaId));
    }
}
