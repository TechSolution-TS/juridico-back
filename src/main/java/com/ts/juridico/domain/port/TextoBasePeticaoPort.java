package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.TextoBasePeticao;

public interface TextoBasePeticaoPort {

    TextoBasePeticao findByFoundation(String foundation);
}
