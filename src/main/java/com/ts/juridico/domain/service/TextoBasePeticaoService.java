package com.ts.juridico.domain.service;

import com.ts.juridico.domain.model.TextoBasePeticao;
import com.ts.juridico.domain.port.TextoBasePeticaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class TextoBasePeticaoService {

    private final TextoBasePeticaoPort textoBasePeticaoPort;

    public TextoBasePeticao searchTextBaseByFoundation(String foundation) {
        return textoBasePeticaoPort.findByFoundation(foundation);
    }
}
