package com.machado0.teste_nt.voto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT v FROM Voto v WHERE v.pauta.id = :pautaId")
    Page<Voto> findByPautaId(Long pautaId, Pageable pageable);

}
