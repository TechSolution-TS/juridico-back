package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.domain.model.Processo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProcessMapper {

    public Processo dataToModel(Long userId, String status, String advogado, String summary) {
        return Processo.builder()
                .userId(userId)
                .processoUuid(UUID.randomUUID().toString())
                .status(status)
                .advogado(advogado)
                .summary(summary)
                .build();
    }
}
