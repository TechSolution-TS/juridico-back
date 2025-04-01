package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_contrato_empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioContratoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @Column(length = 100)
    private String cargo;

    @Column(precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "ultima_remuneracao", precision = 10, scale = 2)
    private BigDecimal ultimaRemuneracao;

    @Column(name = "data_demissao")
    private LocalDate dataDemissao;

    @Column(name = "data_aviso_previo")
    private LocalDate dataAvisoPrevio;

    @Column(name = "tipo_admissao", length = 50)
    private String tipoAdmissao;

    @Column(name = "tipo_demissao", length = 50)
    private String tipoDemissao;

    @Column(name = "tipo_aviso_previo", length = 50)
    private String tipoAvisoPrevio;

    @Column(name = "tipo_beneficio", length = 50)
    private String tipoBeneficio;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsuarioProcesso userId;
}
