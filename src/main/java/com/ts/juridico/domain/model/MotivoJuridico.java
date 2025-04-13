package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motivo_juridico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotivoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "titulo_motivo", columnDefinition = "TEXT")
    private String tituloMotivo;

    @Column(name = "label_fundamento_juridico")
    private String labelFundamento;

    @Column(columnDefinition = "TEXT")
    private String explicacao;

    @ManyToOne
    @JoinColumn(name = "fundamento_juridico_id")
    private FundamentoJuridico fundamentoJuridico;
}
