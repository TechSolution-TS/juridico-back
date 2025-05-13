package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.AuthRequestDto;
import com.ts.juridico.application.dto.response.AuthResponseDto;
import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.infrastructure.util.JwtUtil;
import com.ts.juridico.infrastructure.util.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdvogadoService advogadoService;
    private final JwtUtil jwtUtil;


    public AuthResponseDto login(AuthRequestDto authRequest) {
        Usuario advogado = advogadoService.findByUserLogin(authRequest.getUsername());

        if (Password.verifyPassword(authRequest.getPassword(), advogado.getPassword())) {
            return new AuthResponseDto(jwtUtil.generateToken(advogado.getName()), advogado.getUuid());
        }

        return null;
    }
}
