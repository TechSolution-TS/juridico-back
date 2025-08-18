package com.ts.juridico.application.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class UsuarioProcessoDto {

    private String rg;
    private String cpf;
    private String nome;
    private String estadoCivil;
    private String endereco;
    private LocalDate dataNascimento;
    private String areaAtuacao;
    private String telefone;
    private String senhaGov;
    List<ProcessoDto> processos;
}
