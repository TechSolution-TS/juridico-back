package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.Peticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeticaoJpaRepository extends JpaRepository<Peticao, Long> {
    Optional<Peticao> findByValue(String modeloPetition);
}
