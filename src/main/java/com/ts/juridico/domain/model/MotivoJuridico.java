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

    @Column(length = 100)
    private String motivo;

    @Column(length = 50)
    private String explicacao;
    @ManyToOne
    @JoinColumn(name = "fundamento_juridico_id")
    private FundamentoJuridico fundamentoJuridico;
}
