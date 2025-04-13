package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;
import com.ts.juridico.domain.port.PeticaoFundamentoMotivoPort;
import com.ts.juridico.infrastructure.exception.PetitionNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.FundamentoJuridicoJpaRepository;
import com.ts.juridico.infrastructure.persistence.jpa.MotivoJuridicoJpaRepository;
import com.ts.juridico.infrastructure.persistence.jpa.PeticaoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PeticaoFundamentoMotivoAdapter implements PeticaoFundamentoMotivoPort {

    private final PeticaoJpaRepository peticaoJpaRepository;
    private final FundamentoJuridicoJpaRepository fundamentoJuridicoJpaRepository;
    private final MotivoJuridicoJpaRepository motivoJuridicoJpaRepository;

    @Override
    public List<Peticao> searchPetitions() {
        return peticaoJpaRepository.findAll();
    }

    @Override
    public Peticao searchPetition(String modeloPetition) {
        return peticaoJpaRepository.findByValue(modeloPetition).orElseThrow(() -> new PetitionNotFoundException("Petition Not Found"));
    }

    @Override
    public List<FundamentoJuridico> searchFoundationByPetition(Long petitionId) {
        return fundamentoJuridicoJpaRepository.findByPeticao_id(petitionId);
    }

    @Override
    public FundamentoJuridico searchFoundationByTypePetition(String typeFoundation) {
        return fundamentoJuridicoJpaRepository.findByValue(typeFoundation).orElseThrow(() -> new PetitionNotFoundException("Foundation Not Found"));
    }

    @Override
    public List<MotivoJuridico> searchReasonByFoundation(Long foundationId) {
        return motivoJuridicoJpaRepository.findByFundamentoJuridico_id(foundationId);
    }
}
