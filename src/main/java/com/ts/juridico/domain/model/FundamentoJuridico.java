package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fundamento_juridico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundamentoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String hipotese;

    @Column(length = 100)
    private String value;

    @ManyToOne
    @JoinColumn(name = "peticao_id")
    private Peticao peticao;
}
