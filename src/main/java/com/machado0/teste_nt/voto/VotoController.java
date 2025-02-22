package com.machado0.teste_nt.voto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Object> criar(@RequestBody VotoDTO voto) {
        VotoDTO votoCriado;
        try {
            votoCriado = votoService.criar(voto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sessão encerrada");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(votoCriado);
    }

    @GetMapping
    public ResponseEntity<Page<VotoDTO>> listarTodos(@PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<VotoDTO> votos = votoService.listarTodos(pageable);
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoDTO> buscarPorId(@PathVariable Long id) {
        VotoDTO voto = votoService.buscarPorId(id);
        return ResponseEntity.ok(voto);
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
                                                          @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(votoService.listarResultados(pautaId, pageable));
    }
}
