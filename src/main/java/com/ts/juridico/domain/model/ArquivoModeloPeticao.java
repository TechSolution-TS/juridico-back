package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "arquivo_modelo_peticao")
public class ArquivoModeloPeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "rquivo_id")
    private String arquivoId;

    @Column(name = "link_visualizar")
    private String linkVisualizar;

    @Column(name = "link_download")
    private String linkDownload;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "advogado")
    private String advogado;
}
