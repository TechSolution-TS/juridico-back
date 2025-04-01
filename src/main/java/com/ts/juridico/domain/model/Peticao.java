package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "peticao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Peticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_peticao", length = 100)
    private String tipoPeticao;

    @Column(length = 100)
    private String modelo;

    @Column(length = 100)
    private String value;
}
