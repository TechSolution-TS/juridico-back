package com.ts.juridico.application.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorDto {
    private String code;
    private String message;
}