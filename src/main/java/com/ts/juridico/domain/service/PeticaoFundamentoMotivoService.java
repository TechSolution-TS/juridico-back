package com.ts.juridico.domain.service;

import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;
import com.ts.juridico.domain.port.PeticaoFundamentoMotivoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeticaoFundamentoMotivoService {

    private final PeticaoFundamentoMotivoPort peticaoFundamentoMotivoPort;

    public List<Peticao> searchPetitions() {
       return peticaoFundamentoMotivoPort.searchPetitions();
    }

    public Peticao searchPetition(String modeloPetition) {
        return peticaoFundamentoMotivoPort.searchPetition(modeloPetition);
    }

    public List<FundamentoJuridico> searchFoundations(Long petitionsId) {
        return peticaoFundamentoMotivoPort.searchFoundationByPetition(petitionsId);
    }

    public FundamentoJuridico searchFoundation(String typeFoundation) {
        return peticaoFundamentoMotivoPort.searchFoundationByTypePetition(typeFoundation);
    }

    public List<MotivoJuridico> searchReasons(Long foundationId) {
        return peticaoFundamentoMotivoPort.searchReasonByFoundation(foundationId);
    }
}
