package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.domain.model.Processo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProcessMapper {

    public Processo dataToModel(Long userId, String status, String advogado, String summary, String tribunal, String processoUuid) {
        return Processo.builder()
                .userId(userId)
                .processoUuid(processoUuid)
                .status(status)
                .advogado(advogado)
                .summary(summary)
                .tribunal(tribunal)
                .build();
    }
}
