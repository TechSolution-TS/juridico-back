package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.domain.model.TextoBasePeticao;
import com.ts.juridico.domain.port.TextoBasePeticaoPort;
import com.ts.juridico.infrastructure.exception.PetitionNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.TextoBasePeticaoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TextoBasePeticaoAdapter implements TextoBasePeticaoPort {

    private final TextoBasePeticaoJpaRepository textoBasePeticaoJpaRepository;

    @Override
    public TextoBasePeticao findByFoundation(String foundation) {
        return textoBasePeticaoJpaRepository.findByLabelFundamentoJuridico(foundation).orElseThrow(() -> new PetitionNotFoundException("Text Base Petition Not Found"));
    }
}
