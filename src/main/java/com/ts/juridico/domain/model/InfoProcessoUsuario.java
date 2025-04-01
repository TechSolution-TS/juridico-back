package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "info_processo_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoProcessoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String relatorio;

    @Lob
    private String observacao;

    @Column(length = 100)
    private String cidade;

    @Column(name = "data_processo")
    private LocalDate dataProcesso;

    @Column(name = "advogado_responsavel", length = 100)
    private String advogadoResponsavel;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsuarioProcesso userId;
}
