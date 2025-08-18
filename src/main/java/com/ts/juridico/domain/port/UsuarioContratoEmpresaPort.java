package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import com.ts.juridico.domain.model.UsuarioProcesso;

public interface UsuarioContratoEmpresaPort {

    UsuarioContratoEmpresa saveContractEnterprise(UsuarioProcessoCadastroDto dto);
    UsuarioContratoEmpresa findContractEnterprise(Long userId);
    UsuarioContratoEmpresa findContractEnterpriseByProcessUuid(String processoUuid);
    UsuarioContratoEmpresa findContractEnterpriseByUser(UsuarioProcesso user);
}
