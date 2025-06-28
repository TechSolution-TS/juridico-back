package com.ts.juridico.application.dto.response;

import com.ts.juridico.domain.model.UsuarioProcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UsuarioProcessoCadastroResponseDto {

    private String rg;
    private String cpf;
    private String nome;
    private String estadoCivil;
    private String endereco;
    private String dataNascimento;
    private String areaAtuacao;
    private String telefone;
    private String senhaGov;
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private String enderecoEmpresa;
    private String cargo;
    private BigDecimal salario;
    private BigDecimal ultimaRemuneracao;
    private String dataAdmissao;
    private String dataDemissao;
    private String dataAvisoPrevio;
    private String tipoAdmissao;
    private String tipoDemissao;
    private String tipoAvisoPrevio;
    private String tipoBeneficio;
    private String relatorio;
    private String observacao;
    private String cidade;
    private String dataProcesso;
    private String advogadoResponsavel;
    private String tribunal;
    private String numeroProcesso;
    private String status;
}
