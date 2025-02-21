package com.machado0.teste_nt.associado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public Associado criar(Associado associado) {
        return associadoRepository.save(associado);
    }

    public Associado buscarPorId(Long id) {
        return associadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));
    }

    public Page<Associado> listarTodos(Pageable pageable) {
        return associadoRepository.findAll(pageable);
    }

    public void excluir(Long id) {
        associadoRepository.deleteById(id);
    }

    public Associado atualizar(Associado associado) {
        if (!associadoRepository.existsById(associado.getId())) {
            throw new RuntimeException("Associado não encontrado");
        }

        return associadoRepository.save(associado);
    }
}
