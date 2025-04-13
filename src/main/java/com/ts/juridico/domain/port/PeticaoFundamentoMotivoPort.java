package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;

import java.util.List;

public interface PeticaoFundamentoMotivoPort {

    List<Peticao> searchPetitions();
    Peticao searchPetition(String modeloPetition);
    List<FundamentoJuridico> searchFoundationByPetition(Long petitionId);
    FundamentoJuridico searchFoundationByTypePetition(String typeFoundation);
    List<MotivoJuridico> searchReasonByFoundation(Long foundationId);
}
