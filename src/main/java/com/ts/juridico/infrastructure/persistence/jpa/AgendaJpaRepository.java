package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaJpaRepository extends JpaRepository<Agenda, Long> {
}
