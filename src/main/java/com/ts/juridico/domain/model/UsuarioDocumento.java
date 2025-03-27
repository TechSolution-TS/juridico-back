package com.ts.juridico.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_drive", length = 255)
    private String linkDrive;
}
