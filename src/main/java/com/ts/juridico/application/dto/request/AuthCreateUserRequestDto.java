package com.ts.juridico.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthCreateUserRequestDto {

    private String name;
    private String username;
    private String password;
    private String role;
}
