package com.ts.juridico.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthCreateUserResponseDto {

    private String name;
    private String username;
    private String role;
}
