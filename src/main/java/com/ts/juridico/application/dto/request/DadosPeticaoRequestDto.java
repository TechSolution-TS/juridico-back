package com.ts.juridico.application.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class DadosPeticaoRequestDto {

    private List<String> advogadosReclamante;
    private List<String> advogadosReclamado;
    private String numeroProcesso;

    private Boolean inconstitucionalidade223G;

    @JsonProperty("nome_cliente")
    private String nomeCliente;
    private String nacionalidade = "brasileiro";
    private String sexo;
    @JsonProperty("estado_civil")
    private String estadoCivil;
    private String profissao;
    private String rg;
    private String cpf;
    @JsonProperty("endereco_cliente")
    private String enderecoCliente;

    @JsonProperty("razao_social")
    private String razaoSocial;

    private String cnpj;

    @JsonProperty("endereco_empresa")
    private String enderecoEmpresa;

    // === DADOS CONTRATUAIS ===
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

    @JsonProperty("tem_horas_extras")
    private boolean temHorasExtras = false;

    @JsonProperty("tem_dano_moral")
    private boolean temDanoMoral = false;

    @JsonProperty("tem_intervalo_interjornada")
    private boolean temIntervaloInterjornada = false;

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
}
