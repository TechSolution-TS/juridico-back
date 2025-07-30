package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.DadosChatClienteRequestDto;
import com.ts.juridico.application.dto.request.DadosChatJuridicoRequestDto;
import com.ts.juridico.application.dto.request.DadosPeticaoRequestDto;
import com.ts.juridico.application.dto.response.PeticaoResponseDto;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.service.GoogleDriveService;
import com.ts.juridico.domain.service.OpenAiChatService;
import com.ts.juridico.domain.service.PeticaoPdfService;
import com.ts.juridico.infrastructure.util.TemplatesPadroesPeticao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class OpenAiController {

    private final OpenAiChatService openAiChatService;
    private final GoogleDriveService googleDriveService;
    private final PeticaoPdfService peticaoPdfService;
    private final TemplatesPadroesPeticao templatesPadroesPeticao;

    @GetMapping("/{fileId}/summary")
    public ResponseEntity<String> getResumo(@PathVariable("fileId") String fileId) {
        ArquivoModeloPeticao arquivoModeloPeticao = googleDriveService.searchFileByFileId(fileId);
        String resumo = openAiChatService.summaryPetitionChat(arquivoModeloPeticao);

        return ResponseEntity.ok(resumo);
    }

    @PostMapping("/chat")
    public ResponseEntity<String> getChat(@RequestBody DadosChatJuridicoRequestDto solicitacao) {
        String response = openAiChatService.juridicoChat(solicitacao.getSolicitacao());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat-cliente")
    public ResponseEntity<String> getChatCliente(@RequestBody DadosChatClienteRequestDto solicitacao) {
        String response = openAiChatService.chatCliente(solicitacao);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate/petition")
    public ResponseEntity<PeticaoResponseDto> gerarPeticao(@RequestBody DadosPeticaoRequestDto dados) {
        try {
            dados.configurarPedidosAutomaticos();

            String peticao = openAiChatService.gerarPeticaoCompleta(dados);

            PeticaoResponseDto response = new PeticaoResponseDto();
            response.setPeticao(peticao);
            response.setSucesso(true);
            response.setMensagem("Petição gerada com sucesso");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            PeticaoResponseDto response = new PeticaoResponseDto();
            response.setSucesso(false);
            response.setMensagem("Erro ao gerar petição: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/generate/new_petition")
    public ResponseEntity<PeticaoResponseDto> gerarNovaPeticao(@RequestBody DadosPeticaoRequestDto dados) {
        try {
            dados.configurarPedidosAutomaticos();

            String capa = templatesPadroesPeticao.gerarCapaProcesso(dados);
            String textoIntro = templatesPadroesPeticao.gerarTextoIntro(dados);
            String textoReclamacao = templatesPadroesPeticao.gerarTextoReclamacao(dados);
            String textoJusticaGratuita = templatesPadroesPeticao.gerarTextoJusticaGratuita(dados);
            String textoHonorariosSucumbenciais = templatesPadroesPeticao.gerarTextoHonorariosSucumbenciais(dados);
            String textoInconstitucionalidade223G = null;
            String textoContratoTrabalho = templatesPadroesPeticao.gerarTextoContratoTrabalho(dados);
            String textoTextoCtpDifJornada = templatesPadroesPeticao.gerarTextoCtpDifJornada(dados);
            String textoFuncaoServico = templatesPadroesPeticao.generateTextPetitionChat(dados.getDescricaoFuncaoServico());

            if (dados.getInconstitucionalidade223G()) {
                textoInconstitucionalidade223G = templatesPadroesPeticao.gerarTextoInconstitucionalidade223G();
            }
//            String peticao = openAiChatService.gerarPeticaoCompleta(dados);

            PeticaoResponseDto response = new PeticaoResponseDto();
            response.setPeticao(null);
            response.setCapa(capa);
            response.setVara(dados.getVara());
            response.setTextoIntro(textoIntro);
            response.setTextoReclamacao(textoReclamacao);
            response.setTextoJusticaGratuita(textoJusticaGratuita);
            response.setTextoHonorariosSucumbenciais(textoHonorariosSucumbenciais);
            response.setTextoInconstitucionalidade223G(textoInconstitucionalidade223G);
            response.setJuizoDigital(dados.getJuizoDigital());
            response.setMotivoRescisao(dados.getMotivoRescisao());
            response.setTextoContratoTrabalho(textoContratoTrabalho);
            response.setTextoTextoCtpDifJornada(textoTextoCtpDifJornada);
            response.setTextoFuncoesServicosGerais(textoFuncaoServico);
            response.setDoMerito(templatesPadroesPeticao.gerarTextoDoMerito(dados));
            response.setSucesso(true);
            response.setMensagem("Petição gerada com sucesso");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            PeticaoResponseDto response = new PeticaoResponseDto();
            response.setSucesso(false);
            response.setMensagem("Erro ao gerar petição: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/generate/petition/pdf")
    public ResponseEntity<byte[]> gerarPdfPeticao(@RequestBody PeticaoResponseDto peticaoGerada) {
        byte[] pdf = peticaoPdfService.gerarPdfPeticao(peticaoGerada);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=peticao_ts_juridico.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}