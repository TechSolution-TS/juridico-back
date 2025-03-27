package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioProcesso;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioProcesssoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.UsuarioProcessoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioProcessoAdapter implements UsuarioProcessoPort {

    private final UsuarioProcesssoJpaRepository usuarioProcesssoJpaRepository;
    private final UsuarioProcessoMapper mapper;

    @Override
    public UsuarioProcesso saveUserProcess(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto) {
        UsuarioProcesso user = mapper.dtoToModel(usuarioProcessoCadastroDto);
        return usuarioProcesssoJpaRepository.save(user);
    }
}
