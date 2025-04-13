package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.response.FundamentoJuridicoDto;
import com.ts.juridico.application.dto.response.MotivoJuridicoDto;
import com.ts.juridico.application.dto.response.PeticaoDto;
import com.ts.juridico.application.mapper.PeticaoFundamentoMotivoMapper;
import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;
import com.ts.juridico.infrastructure.persistence.jpa.service.PeticaoFundamentoMotivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petitions")
@RequiredArgsConstructor
public class PeticaoController {

    private final PeticaoFundamentoMotivoService peticaoFundamentoMotivoService;
    private final PeticaoFundamentoMotivoMapper peticaoFundamentoMotivoMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PeticaoDto>> getPettions() {
        List<Peticao> petitions = peticaoFundamentoMotivoService.searchPetitions();
        return ResponseEntity.ok(peticaoFundamentoMotivoMapper.tolistDto(petitions));
    }

    @GetMapping(value = "/foundations/{modeloPetition}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FundamentoJuridicoDto>> getFoundations(@PathVariable("modeloPetition") String modeloPetition) {
        Peticao petition = peticaoFundamentoMotivoService.searchPetition(modeloPetition);
        List<FundamentoJuridico> foundations = peticaoFundamentoMotivoService.searchFoundations(petition.getId());
        return ResponseEntity.ok(peticaoFundamentoMotivoMapper.tolistFundationDto(foundations));
    }

    @GetMapping(value = "/reasons/{typeFoundation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MotivoJuridicoDto>> getReasons(@PathVariable("typeFoundation") String typeFoundation) {
        FundamentoJuridico foundation = peticaoFundamentoMotivoService.searchFoundation(typeFoundation);
        List<MotivoJuridico> reasons = peticaoFundamentoMotivoService.searchReasons(foundation.getId());
        return ResponseEntity.ok(peticaoFundamentoMotivoMapper.tolistReasonDto(reasons));
    }
}
