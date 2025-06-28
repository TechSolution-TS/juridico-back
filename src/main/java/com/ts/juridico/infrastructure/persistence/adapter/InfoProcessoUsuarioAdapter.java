package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.InfoProcessoUsuario;
import com.ts.juridico.domain.port.InfoProcessoUsuarioPort;
import com.ts.juridico.infrastructure.exception.UserNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.InfoProcessoUsuarioJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.InfoProcessoUsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InfoProcessoUsuarioAdapter implements InfoProcessoUsuarioPort {

    private final InfoProcessoUsuarioJpaRepository infoProcessoUsuarioJpaRepository;
    private final InfoProcessoUsuarioMapper infoProcessoUsuarioMapper;

    @Override
    public InfoProcessoUsuario saveInfoProcessUser(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto) {
        InfoProcessoUsuario infoProcessoUsuario = infoProcessoUsuarioMapper.dtoToModel(usuarioProcessoCadastroDto);
        return infoProcessoUsuarioJpaRepository.save(infoProcessoUsuario);
    }

    @Override
    public InfoProcessoUsuario findInfoProcessUser(Long userId) {
        return infoProcessoUsuarioJpaRepository.findByUserId_id(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Info with ID " + userId + " not found"));
    }

    @Override
    public InfoProcessoUsuario findInfoProcessUserByProcessoUuid(String processoUuid) {
        return infoProcessoUsuarioJpaRepository.findByProcessoUuid(processoUuid)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Info with Processo " + processoUuid + " not found"));
    }

    @Override
    public InfoProcessoUsuario save(InfoProcessoUsuario infoProcessoUsuario) {
        return infoProcessoUsuarioJpaRepository.save(infoProcessoUsuario);
    }
}
