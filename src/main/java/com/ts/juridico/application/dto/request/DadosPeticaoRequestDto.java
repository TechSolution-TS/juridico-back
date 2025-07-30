package com.ts.juridico.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DadosPeticaoRequestDto {

    @JsonProperty("advogados_reclamante")
    private List<String> advogadosReclamante;
    @JsonProperty("advogados_reclamado")
    private List<String> advogadosReclamado;
    @JsonProperty("numero_processo")
    private String numeroProcesso;

    private Boolean inconstitucionalidade223G;
    @JsonProperty("juizo_digital")
    private Boolean juizoDigital;
    @JsonProperty("tem_intervalo_interjornada")
    private boolean temIntervaloInterjornada = false;
    @JsonProperty("tem_horas_extras")
    private boolean temHorasExtras = false;
    @JsonProperty("tem_ctps_assinada")
    private boolean temCtpsAssinada = false;

    @JsonProperty("motivo_rescisao")
    private Integer motivoRescisao;

    @JsonProperty("motivos_rescisao_alineas")
    private List<String>  motivosRescisaoAlineas;

    @JsonProperty("descricao_funcao_servico")
    private String descricaoFuncaoServico;

    @JsonProperty("nome_cliente")
    private String nomeCliente;
    private String nacionalidade = "brasileiro";
    private String sexo;

    @JsonProperty("estado_civil")
    private String estadoCivil;

    private String profissao;
    private String rg;
    private String cpf;

    // Endereço do cliente decomposto
    @JsonProperty("endereco_cliente")
    private String enderecoCliente; // Campo opcional (texto livre)
    @JsonProperty("rua_cliente")
    private String ruaCliente;
    @JsonProperty("numero_endereco_cliente")
    private String numeroEnderecoCliente;
    @JsonProperty("bairro_cliente")
    private String bairroCliente;
    @JsonProperty("cep_cliente")
    private String cepCliente;
    @JsonProperty("cidade_cliente")
    private String cidadeCliente;
    @JsonProperty("estado_cliente")
    private String estadoCliente;

    // Empresa
    @JsonProperty("razao_social")
    private String razaoSocial;
    private String cnpj;

    // Endereço da empresa decomposto
    @JsonProperty("endereco_empresa")
    private String enderecoEmpresa; // Campo opcional (texto livre)
    @JsonProperty("rua_empresa")
    private String ruaEmpresa;
    @JsonProperty("numero_endereco_empresa")
    private String numeroEnderecoEmpresa;
    @JsonProperty("bairro_empresa")
    private String bairroEmpresa;
    @JsonProperty("cep_empresa")
    private String cepEmpresa;
    @JsonProperty("cidade_empresa")
    private String cidadeEmpresa;
    @JsonProperty("estado_empresa")
    private String estadoEmpresa;

    // Dados contratuais
    @JsonProperty("data_admissao")
    private String dataAdmissao;
    @JsonProperty("data_rescisao")
    private String dataRescisao;
    private String funcao;
    private String salario;
    private String jornada;
    private String regime;

    private List<String> pedidos;

    @JsonProperty("tem_insalubridade")
    private boolean temInsalubridade = false;
    @JsonProperty("tem_adicional_noturno")
    private boolean temAdicionalNoturno = false;

    @JsonProperty("tem_dano_moral")
    private boolean temDanoMoral = false;

    private String vara;

    @JsonProperty("valor_causa")
    private String valorCausa;

    public void configurarPedidosAutomaticos() {
        if (pedidos != null) {
            temInsalubridade = pedidos.stream().anyMatch(p -> p.toLowerCase().contains("insalubr"));
            temAdicionalNoturno = pedidos.stream().anyMatch(p -> p.toLowerCase().contains("noturno"));
            temHorasExtras = pedidos.stream().anyMatch(p -> p.toLowerCase().contains("horas extras"));
            temDanoMoral = pedidos.stream().anyMatch(p -> p.toLowerCase().contains("dano moral"));
            temIntervaloInterjornada = pedidos.stream().anyMatch(p -> p.toLowerCase().contains("interjornada"));
        }
    }

    // Métodos auxiliares (opcional)
    public String getEnderecoClienteFormatado() {
        return String.format("%s, %s, %s, CEP %s, %s/%s",
                ruaCliente, numeroEnderecoCliente, bairroCliente, cepCliente, cidadeCliente, estadoCliente);
    }

    public String getEnderecoEmpresaFormatado() {
        return String.format("%s, %s, %s, CEP %s, %s/%s",
                ruaEmpresa, numeroEnderecoEmpresa, bairroEmpresa, cepEmpresa, cidadeEmpresa, estadoEmpresa);
    }

    public String getMunicipioEmpresa() {
        return cidadeEmpresa + "/" + estadoEmpresa;
    }
}
