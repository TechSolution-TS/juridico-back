package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.AuthCreateUserRequestDto;
import com.ts.juridico.application.dto.request.AuthRequestDto;
import com.ts.juridico.application.dto.response.AuthCreateUserResponseDto;
import com.ts.juridico.application.dto.response.AuthResponseDto;
import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.infrastructure.util.JwtUtil;
import com.ts.juridico.infrastructure.util.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdvogadoService advogadoService;
    private final JwtUtil jwtUtil;


    public AuthResponseDto login(AuthRequestDto authRequest) {
        Usuario advogado = advogadoService.findByUserLogin(authRequest.getUsername());

        if (Password.verifyPassword(authRequest.getPassword(), advogado.getPassword())) {
            return new AuthResponseDto(jwtUtil.generateToken(advogado.getName()), advogado.getUuid(), advogado.getRole(),advogado.getName(), advogado.getLogin());
        }

        return null;
    }

    public Usuario create(AuthCreateUserRequestDto authRequest) {
        Usuario advogado = Usuario.builder()
                .uuid(UUID.randomUUID().toString())
                .name(authRequest.getName())
                .role(authRequest.getRole())
                .login(authRequest.getUsername())
                .password(Password.hashPassword(authRequest.getPassword()))
                .build();

        advogadoService.save(advogado);

        return advogado;
    }

    public List<AuthCreateUserResponseDto>  findAll() {
        List<AuthCreateUserResponseDto> authCreateUserResponseDtos = new ArrayList<>();
        List<Usuario> all = advogadoService.findAll();

        all.forEach(user -> {
            authCreateUserResponseDtos.add(AuthCreateUserResponseDto.builder()
                            .name(user.getName())
                            .username(user.getLogin())
                            .role(user.getRole())
                    .build());
        });
        return authCreateUserResponseDtos;
    }
}
