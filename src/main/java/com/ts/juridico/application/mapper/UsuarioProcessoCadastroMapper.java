package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.dto.response.UsuarioProcessoCadastroResponseDto;
import com.ts.juridico.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioProcessoCadastroMapper {

    public UsuarioProcessoCadastroDto modelToDto(UsuarioProcesso user,
                                                 UsuarioEmpresaProcesso empresaProcesso,
                                                 UsuarioContratoEmpresa usuarioContratoEmpresa,
                                                 InfoProcessoUsuario infoProcessoUsuario) {
        return UsuarioProcessoCadastroDto.builder()
                .id(user.getId())
                .rg(user.getRg())
                .cpf(user.getCpf())
                .nome(user.getNome())
                .estadoCivil(user.getEstadoCivil())
                .endereco(user.getEndereco())
                .dataNascimento(user.getDataNascimento().toString())
                .areaAtuacao(user.getAreaAtuacao())
                .telefone(user.getTelefone())
                .senhaGov(user.getSenhaGov())
                .nomeEmpresa(empresaProcesso.getNome())
                .cnpjEmpresa(empresaProcesso.getCnpj())
                .enderecoEmpresa(empresaProcesso.getEndereco())
                .cargo(usuarioContratoEmpresa.getCargo())
                .salario(usuarioContratoEmpresa.getSalario())
                .ultimaRemuneracao(usuarioContratoEmpresa.getUltimaRemuneracao())
                .dataAdmissao(usuarioContratoEmpresa.getDataAdmissao().toString().toString())
                .dataDemissao(usuarioContratoEmpresa.getDataDemissao().toString())
                .dataAvisoPrevio(usuarioContratoEmpresa.getDataAvisoPrevio().toString())
                .tipoAdmissao(usuarioContratoEmpresa.getTipoAdmissao())
                .tipoDemissao(usuarioContratoEmpresa.getTipoDemissao())
                .tipoAvisoPrevio(usuarioContratoEmpresa.getTipoAvisoPrevio())
                .tipoBeneficio(usuarioContratoEmpresa.getTipoBeneficio())
                .relatorio(infoProcessoUsuario.getRelatorio())
                .observacao(infoProcessoUsuario.getObservacao())
                .cidade(infoProcessoUsuario.getCidade())
                .dataProcesso(infoProcessoUsuario.getDataProcesso().toString())
                .advogadoResponsavel(infoProcessoUsuario.getAdvogadoResponsavel())
                .processoUuid(infoProcessoUsuario.getProcessoUuid())
                .build();
    }

    public UsuarioProcessoCadastroResponseDto modelToResponseDto(UsuarioProcesso user,
                                                                 UsuarioEmpresaProcesso empresaProcesso,
                                                                 UsuarioContratoEmpresa usuarioContratoEmpresa,
                                                                 InfoProcessoUsuario infoProcessoUsuario, Processo processo) {
        return UsuarioProcessoCadastroResponseDto.builder()
                .rg(user.getRg())
                .cpf(user.getCpf())
                .nome(user.getNome())
                .estadoCivil(user.getEstadoCivil())
                .endereco(user.getEndereco())
                .dataNascimento(user.getDataNascimento().toString())
                .areaAtuacao(user.getAreaAtuacao())
                .telefone(user.getTelefone())
                .senhaGov(user.getSenhaGov())
                .nomeEmpresa(empresaProcesso.getNome())
                .cnpjEmpresa(empresaProcesso.getCnpj())
                .enderecoEmpresa(empresaProcesso.getEndereco())
                .cargo(usuarioContratoEmpresa.getCargo())
                .salario(usuarioContratoEmpresa.getSalario())
                .ultimaRemuneracao(usuarioContratoEmpresa.getUltimaRemuneracao())
                .dataAdmissao(usuarioContratoEmpresa.getDataAdmissao() != null ? usuarioContratoEmpresa.getDataAdmissao().toString() : null)
                .dataDemissao(usuarioContratoEmpresa.getDataDemissao() != null ? usuarioContratoEmpresa.getDataDemissao().toString() : null)
                .dataAvisoPrevio(usuarioContratoEmpresa.getDataAvisoPrevio() != null ? usuarioContratoEmpresa.getDataAvisoPrevio().toString() : null)
                .tipoAdmissao(usuarioContratoEmpresa.getTipoAdmissao())
                .tipoDemissao(usuarioContratoEmpresa.getTipoDemissao())
                .tipoAvisoPrevio(usuarioContratoEmpresa.getTipoAvisoPrevio())
                .tipoBeneficio(usuarioContratoEmpresa.getTipoBeneficio())
                .relatorio(infoProcessoUsuario.getRelatorio())
                .observacao(infoProcessoUsuario.getObservacao())
                .cidade(infoProcessoUsuario.getCidade())
                .dataProcesso(infoProcessoUsuario.getDataProcesso().toString())
                .advogadoResponsavel(infoProcessoUsuario.getAdvogadoResponsavel())
                .tribunal(processo.getTribunal())
                .status(processo.getStatus())
                .numeroProcesso(processo.getNumeroProcesso())
                .build();
    }
}
