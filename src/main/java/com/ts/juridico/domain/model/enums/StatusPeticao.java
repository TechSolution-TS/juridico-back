package com.ts.juridico.domain.model.enums;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPeticao {

    MODELO("modelo", "modelo"),
    DELETADO("deletado", "deletado"),
    REJEITADO("rejeitado", "rejeitado"),
    PENDENTE("pendente", "pendente"),
    ANALISE("peticao_pendente", "analise"),
    CONCLUIDO("concluido", "concluido");

    private String tipo;
    private String status;
}
