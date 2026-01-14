package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data")
    private String data;

    @Column(name = "horario")
    private String horario;

    @Column(name = "numero_processo")
    private String numeroProcesso;

    @Column(name = "advogados", columnDefinition = "TEXT")
    private String advogados;
}
