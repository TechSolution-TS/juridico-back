package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "arquivos_documentos_sindicatos")
public class ArquivosDocumentosSindicatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "arquivo_id")
    private String arquivoId;

    @Column(name = "link_visualizar")
    private String linkVisualizar;

    @Column(name = "link_download")
    private String linkDownload;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "documento_sindicato_uuid")
    private String documentoSindicatoUuid;
}
