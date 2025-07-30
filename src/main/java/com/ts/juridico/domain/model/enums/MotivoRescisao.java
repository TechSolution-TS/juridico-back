package com.ts.juridico.domain.model.enums;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MotivoRescisao {

    RESCISAO_INDIRETA(1, "RESCISÃO INDIRETA");

    private Integer id;
    private String motivo;

    public static String searchReason(Integer id) {
        for (MotivoRescisao motivo : MotivoRescisao.values()) {
            if (motivo.getId() == id) {
                return motivo.getMotivo();
            }
        }
        throw new EntityNotFoundException("Motivo Rescisão não encontrado: '" + id + "'");
    }
}
