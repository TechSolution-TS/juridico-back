package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioProcesso;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import com.ts.juridico.infrastructure.exception.UserNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioProcesssoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.UsuarioProcessoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public UsuarioProcesso findUser(String cpf) {
        return usuarioProcesssoJpaRepository.findByCpf(cpf)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with CPF " + cpf + " not found"));
    }
}
