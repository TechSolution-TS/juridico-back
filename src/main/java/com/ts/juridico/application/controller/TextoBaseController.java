package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.GerarPeticaoRequestDto;
import com.ts.juridico.application.dto.response.TextoBasePeticaoDto;
import com.ts.juridico.application.mapper.TextoBasePeticaoMapper;
import com.ts.juridico.domain.model.TextoBasePeticao;
import com.ts.juridico.domain.service.TextoBasePeticaoService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/text-base")
@RequiredArgsConstructor
public class TextoBaseController {

    private final TextoBasePeticaoService textoBasePeticaoService;
    private final TextoBasePeticaoMapper textoBasePeticaoMapper;

    @GetMapping(value = "/{typeFoundation}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextoBasePeticaoDto> searchTextBasePetition(@PathVariable("typeFoundation") String typeFoundation) {
        TextoBasePeticao textoBasePeticao = textoBasePeticaoService.searchTextBaseByFoundation(typeFoundation);
        return ResponseEntity.ok(textoBasePeticaoMapper.modelToDto(textoBasePeticao));
    }

    @PostMapping(value = "/gerar-peticao/{typeFoundation}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<byte[]> gerarPeticaoPersonalizada(
            @PathVariable("typeFoundation") String typeFoundation,
            @RequestBody GerarPeticaoRequestDto requestDTO
    ) throws IOException {
        TextoBasePeticao texto = textoBasePeticaoService.searchTextBaseByFoundation(typeFoundation);

        XWPFDocument document = new XWPFDocument();

        adicionarParagrafo(document, texto.getEnderecamento(), true);
        adicionarParagrafo(document, texto.getQualificacaoReclamante(), false);
        adicionarParagrafo(document, "RECLAMAÇÃO TRABALHISTA", true);
        adicionarParagrafo(document, texto.getQualificacaoReclamado(), false);

        adicionarSecao(document, texto.getTituloJusticaGratuita(), texto.getPedidoJusticaGratuita());
        adicionarSecao(document, texto.getTituloJuizoDigital(), texto.getPedidoJuizoDigital());
        adicionarSecao(document, texto.getTituloIncensaoHonorarios(), texto.getIncensaoHonorarios());
        adicionarSecao(document, texto.getTituloContratoTrabalho(), texto.getContratoTrabalho());

        adicionarSecao(document, "DO MÉRITO", null);
        adicionarSecao(document, requestDTO.getTituloFundamentoMotivo(), requestDTO.getExplicacaoFundamentoMotivo());
        adicionarSecao(document, requestDTO.getTituloVerbasRescisorias(), requestDTO.getExplicacaoVerbasRescisorias());

        requestDTO.getExplicacaoDireitoSolicitado().forEach(expicacao -> {
            adicionarSecao(document, expicacao.getDireito(), expicacao.getExplicao());
        });

        adicionarSecao(document, texto.getTituloHonorariosSucubencias(), texto.getHonorariosSucubencias());
        adicionarSecao(document, texto.getTituloExibicaoDocumentos(), texto.getExibicaoDocumentos());
        adicionarSecao(document, texto.getTituloPedidos(), texto.getPedidos());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "peticao-gerada.docx");
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentLength(out.size());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(out.toByteArray());
    }
    private void adicionarParagrafo(XWPFDocument doc, String texto, boolean centralizado) {
        XWPFParagraph paragrafo = doc.createParagraph();
        if (centralizado) {
            paragrafo.setAlignment(ParagraphAlignment.CENTER);
        } else {
            paragrafo.setAlignment(ParagraphAlignment.BOTH);
        }

        XWPFRun run = paragrafo.createRun();
        run.setText(texto);
        run.setFontSize(12);
        run.setFontFamily("Times New Roman");
        run.setBold(centralizado);
        run.addCarriageReturn();
    }

    private void adicionarSecao(XWPFDocument doc, String titulo, String corpo) {
        if (titulo != null && !titulo.isBlank()) {
            XWPFParagraph tituloParagrafo = doc.createParagraph();
            tituloParagrafo.setStyle("Heading2");
            XWPFRun tituloRun = tituloParagrafo.createRun();
            tituloRun.setText(titulo);
            tituloRun.setBold(true);
            tituloRun.setFontSize(14);
            tituloRun.setFontFamily("Times New Roman");
            tituloRun.addCarriageReturn();
        }

        if (corpo != null && !corpo.isBlank()) {
            XWPFParagraph corpoParagrafo = doc.createParagraph();
            corpoParagrafo.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun corpoRun = corpoParagrafo.createRun();
            corpoRun.setText(corpo);
            corpoRun.setFontSize(12);
            corpoRun.setFontFamily("Times New Roman");
            corpoRun.addCarriageReturn();
        }
    }

}
