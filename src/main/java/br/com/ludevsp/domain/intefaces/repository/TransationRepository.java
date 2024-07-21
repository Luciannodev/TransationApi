package br.com.ludevsp.domain.intefaces.repository;

import br.com.ludevsp.domain.entities.Transation;
import br.com.ludevsp.domain.entities.TransationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransationRepository extends JpaRepository<Transation, Long> {
}
