package com.machado0.teste_nt.voto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    private final VotoRepository votoRepository;

    @Autowired
    public VotoService(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    public Voto criar(Voto voto) {
        return votoRepository.save(voto);
    }

    public Voto buscarPorId(Long id) {
        return votoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voto não encontrado"));
    }

    public Page<Voto> listarTodos(Pageable pageable) {
        return votoRepository.findAll(pageable);
    }

    public void excluir(Long id) {
        votoRepository.deleteById(id);
    }

    public Voto atualizar(Voto voto) {
        if (!votoRepository.existsById(voto.getId())) {
            throw new RuntimeException("Voto não encontrado");
        }

        return votoRepository.save(voto);
    }
}
