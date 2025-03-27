package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.InfoProcessoUsuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InfoProcessoUsuarioMapper {

    public InfoProcessoUsuario dtoToModel(UsuarioProcessoCadastroDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return InfoProcessoUsuario.builder()
                .relatorio(dto.getRelatorio())
                .observacao(dto.getObservacao())
                .cidade(dto.getCidade())
                .dataProcesso(LocalDate.parse(dto.getDataProcesso(), formatter))
                .advogadoResponsavel(dto.getAdvogadoResponsavel())
                .build();
    }
}
