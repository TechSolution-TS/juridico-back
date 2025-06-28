package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioDocumento;
import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UsuarioProcessoMapper {

    public UsuarioProcesso dtoToModel(UsuarioProcessoCadastroDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return UsuarioProcesso.builder()
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .areaAtuacao(dto.getAreaAtuacao())
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .estadoCivil(dto.getEstadoCivil())
                .dataNascimento(LocalDate.parse(dto.getDataNascimento(), formatter))
                .telefone(dto.getTelefone())
                .senhaGov(dto.getSenhaGov())
                .processoUuid(dto.getProcessoUuid())
                .build();
    }

    public UsuarioDocumento dataToUsuarioDocumentoModel(Long id, String fileId, String processUuid) {
        return UsuarioDocumento.builder()
                .userId(id)
                .fileId(fileId)
                .processUuid(processUuid)
                .build();
    }
}
