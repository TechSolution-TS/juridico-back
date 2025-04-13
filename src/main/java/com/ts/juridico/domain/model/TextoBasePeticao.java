package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "texto_base_peticao")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextoBasePeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String labelFundamentoJuridico;

    @Column()
    private String enderecamento;

    @Column(columnDefinition = "TEXT")
    private String qualificacaoReclamante;

    @Column(columnDefinition = "TEXT")
    private String qualificacaoReclamado;

    @Column()
    private String tituloJusticaGratuita;

    @Column(columnDefinition = "TEXT")
    private String pedidoJusticaGratuita;

    @Column()
    private String tituloJuizoDigital;

    @Column(columnDefinition = "TEXT")
    private String pedidoJuizoDigital;

    @Column()
    private String tituloIncensaoHonorarios;

    @Column(columnDefinition = "TEXT")
    private String incensaoHonorarios;

    @Column()
    private String tituloContratoTrabalho;

    @Column(columnDefinition = "TEXT")
    private String contratoTrabalho;

    @Column()
    private String tituloHonorariosSucubencias;

    @Column(columnDefinition = "TEXT")
    private String honorariosSucubencias;

    @Column()
    private String tituloExibicaoDocumentos;

    @Column(columnDefinition = "TEXT")
    private String exibicaoDocumentos;

    @Column()
    private String tituloPedidos;

    @Column(columnDefinition = "TEXT")
    private String pedidos;
}
