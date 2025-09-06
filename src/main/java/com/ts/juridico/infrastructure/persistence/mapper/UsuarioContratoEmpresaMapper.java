package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UsuarioContratoEmpresaMapper {

    public UsuarioContratoEmpresa dtoToModel(UsuarioProcessoCadastroDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return UsuarioContratoEmpresa.builder()
                .cargo(dto.getCargo())
                .dataAdmissao(!dto.getDataAdmissao().isEmpty() ? LocalDate.parse(dto.getDataAdmissao(), formatter) : null)
                .dataDemissao(!dto.getDataDemissao().isEmpty() ? LocalDate.parse(dto.getDataDemissao(), formatter) : null)
                .dataAvisoPrevio(!dto.getDataAvisoPrevio().isEmpty() ? LocalDate.parse(dto.getDataAvisoPrevio(), formatter) : null)
                .salario(dto.getSalario())
                .tipoBeneficio(dto.getTipoBeneficio())
                .tipoAdmissao(dto.getTipoAdmissao())
                .tipoAvisoPrevio(dto.getTipoAvisoPrevio())
                .tipoDemissao(dto.getTipoDemissao())
                .ultimaRemuneracao(dto.getUltimaRemuneracao())
                .userId(dto.getUsuarioProcesso())
                .processoUuid(dto.getProcessoUuid())
                .build();
    }
}
