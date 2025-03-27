package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import com.ts.juridico.domain.port.UsuarioContratoEmpresaPort;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioContratoEmpresaJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.UsuarioContratoEmpresaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioContratoEmpresaAdapter implements UsuarioContratoEmpresaPort {

    private final UsuarioContratoEmpresaMapper mapper;
    private final UsuarioContratoEmpresaJpaRepository usuarioContratoEmpresaJpaRepository;


    @Override
    public UsuarioContratoEmpresa saveContractEnterprise(UsuarioProcessoCadastroDto dto) {
        UsuarioContratoEmpresa usuarioContratoEmpresa = mapper.dtoToModel(dto);
        return usuarioContratoEmpresaJpaRepository.save(usuarioContratoEmpresa);
    }
}
