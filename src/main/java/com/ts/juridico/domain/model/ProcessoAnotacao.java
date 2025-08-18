package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "processo_anotacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessoAnotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anotacao_uuid", nullable = false, unique = true, length = 36)
    private String anotacaoUuid;

    @Column(name = "processo_uuid", nullable = false)
    private String processoUuid;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "texto", columnDefinition = "TEXT", nullable = false)
    private String texto;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "prioridade", length = 20)
    private String prioridade;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDate dataCriacao;
}
