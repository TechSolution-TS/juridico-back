package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import com.ts.juridico.domain.port.UsuarioEmpresaProcessoPort;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioEmpresaProcessoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.UsuarioEmpresaProcessoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioEmpresaProcessoAdapter implements UsuarioEmpresaProcessoPort {

    private final UsuarioEmpresaProcessoJpaRepository usuarioEmpresaProcessoJpaRepository;
    private final UsuarioEmpresaProcessoMapper mapper;

    @Override
    public UsuarioEmpresaProcesso saveUserEnterprise(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto) {
        UsuarioEmpresaProcesso usuarioEmpresaProcesso = mapper.dtoToModel(usuarioProcessoCadastroDto);
        return usuarioEmpresaProcessoJpaRepository.save(usuarioEmpresaProcesso);
    }
}
