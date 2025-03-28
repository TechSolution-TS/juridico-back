package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;

import java.util.List;

public interface PeticaoFundamentoMotivoPort {

    List<Peticao> searchPetitions();
    List<FundamentoJuridico> searchFoundationByPetition(Long petitionId);
    List<MotivoJuridico> searchReasonByFoundation(Long foundationId);
}
