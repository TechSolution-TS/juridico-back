package com.ts.juridico.infrastructure.persistence.mapper;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEmpresaProcessoMapper {

    public UsuarioEmpresaProcesso dtoToModel(UsuarioProcessoCadastroDto dto) {

        return UsuarioEmpresaProcesso.builder()
                .cnpj(dto.getCnpjEmpresa())
                .nome(dto.getNomeEmpresa())
                .endereco(dto.getEnderecoEmpresa())
                .build();
    }
}
