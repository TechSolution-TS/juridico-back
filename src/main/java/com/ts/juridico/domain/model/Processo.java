package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "processos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "processo_uuid")
    private String processoUuid;

    @Column(name = "usuario_id")
    private Long userId;

    @Column(name = "status")
    private String status;

    @Column(name = "tribunal")
    private String tribunal;

    @CreationTimestamp
    @Column(name = "data_processo", updatable = false)
    private LocalDate dataProcesso;

    @Column(name = "advogado")
    private String advogado;
}
