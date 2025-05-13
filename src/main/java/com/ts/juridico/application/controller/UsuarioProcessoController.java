package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.dto.response.MessageResponseDto;
import com.ts.juridico.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioProcessoCadastroDto> createUserProcess(@PathVariable("cpf") String cpf) {
        UsuarioProcessoCadastroDto userProcess = usuarioService.findUserProcess(cpf);
        return ResponseEntity.ok(userProcess);
    }
}
