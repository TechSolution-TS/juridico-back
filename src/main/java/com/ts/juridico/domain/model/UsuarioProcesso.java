package com.ts.juridico.domain.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_processo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String rg;

    @Column(length = 14)
    private String cpf;

    @Column(length = 100)
    private String nome;

    @Column(name = "estado_civil", length = 50)
    private String estadoCivil;

    @Column(length = 200)
    private String endereco;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao;

    @Column(length = 20)
    private String telefone;

    @Column(name = "senha_gov", length = 100)
    private String senhaGov;
}