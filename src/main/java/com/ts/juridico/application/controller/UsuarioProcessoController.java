package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.dto.response.MessageResponseDto;
import com.ts.juridico.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/process")
@RequiredArgsConstructor
public class UsuarioProcessoController {

    private final UsuarioService usuarioService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> createUserProcess(@RequestBody UsuarioProcessoCadastroDto usuarioProcessoCadastroDto) {
        usuarioService.userRegister(usuarioProcessoCadastroDto);
        return ResponseEntity.ok(new MessageResponseDto("Operação realizada com sucesso!"));
    }
}
