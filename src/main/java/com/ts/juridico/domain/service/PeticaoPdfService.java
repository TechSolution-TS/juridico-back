package com.ts.juridico.domain.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.ts.juridico.application.dto.response.PeticaoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.io.font.FontProgramFactory;

import java.io.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PeticaoPdfService {

    public byte[] gerarPdfPeticao(PeticaoResponseDto peticaoDados) {
        String capa = peticaoDados.getCapa();
        String peticao = peticaoDados.getPeticao();
        String vara = peticaoDados.getVara();
        String textoIntro = peticaoDados.getTextoIntro();
        String textoReclamacao = peticaoDados.getTextoReclamacao();
        String textoJusticaGratuita = peticaoDados.getTextoJusticaGratuita();
        String textoHonorariosSucumbenciais = peticaoDados.getTextoHonorariosSucumbenciais();
        String textoInconstitucionalidade223G = peticaoDados.getTextoInconstitucionalidade223G();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(70, 56.7f, 70, 35); // sup, dir, inf, esq

            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new RodapeEventHandler());

            PdfFont fontNormal = PdfFontFactory.createFont("fonts/times.ttf", PdfEncodings.WINANSI);
            PdfFont fontNegrito = PdfFontFactory.createFont("fonts/timesbd.ttf", PdfEncodings.WINANSI);

            System.out.println("Iniciando geração do PDF...");

            // Verificação de segurança para cada seção
            try {
                System.out.println("Adicionando capa...");
                if (capa != null && !capa.trim().isEmpty()) {
                    adicionarCapaProcesso(document, capa);
                }
                System.out.println("Capa adicionada com sucesso");
            } catch (Exception e) {
                System.err.println("Erro na capa: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                System.out.println("Adicionando intro...");
                if (vara != null && textoIntro != null) {
                    adicionarIntroPeticao(document, vara, textoIntro, fontNormal, fontNegrito);
                }
                System.out.println("Intro adicionada com sucesso");
            } catch (Exception e) {
                System.err.println("Erro na intro: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                System.out.println("Adicionando reclamação...");
                if (textoReclamacao != null && !textoReclamacao.trim().isEmpty()) {
                    adicionarTextoReclamacao(document, textoReclamacao, fontNormal, fontNegrito);
                }
                System.out.println("Reclamação adicionada com sucesso");
            } catch (Exception e) {
                System.err.println("Erro na reclamação: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                System.out.println("Adicionando justiça gratuita...");
                if (textoJusticaGratuita != null && !textoJusticaGratuita.trim().isEmpty()) {
                    adicionarTextoJusticaGratuita(document, textoJusticaGratuita, fontNormal, fontNegrito);
                }
                System.out.println("Justiça gratuita adicionada com sucesso");
            } catch (Exception e) {
                System.err.println("Erro na justiça gratuita: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                System.out.println("Adicionando honorários sucumbenciais...");
                if (textoHonorariosSucumbenciais != null && !textoHonorariosSucumbenciais.trim().isEmpty()) {
                    adicionarTextoHonorariosSucumbenciais(document, textoHonorariosSucumbenciais, fontNormal, fontNegrito);
                }
                System.out.println("Honorários sucumbenciais adicionados com sucesso");
            } catch (Exception e) {
                System.err.println("Erro nos honorários: " + e.getMessage());
                e.printStackTrace();
            }



            // TEXTO DA PETIÇÃO
            try {
                System.out.println("Adicionando texto da petição...");
                if (peticao != null && !peticao.isBlank()) {
                    String[] linhas = peticao.split("\\r?\\n");
                    for (String linha : linhas) {
                        if (linha.trim().isEmpty()) {
                            document.add(new Paragraph(" "));
                        } else if (linha.equals(linha.toUpperCase()) && linha.length() < 80) {
                            document.add(new Paragraph(linha)
                                    .setFont(fontNegrito)
                                    .setFontSize(12)
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setMultipliedLeading(1.5f));
                        } else {
                            document.add(new Paragraph(linha)
                                    .setFont(fontNormal)
                                    .setFontSize(12)
                                    .setTextAlignment(TextAlignment.JUSTIFIED)
                                    .setMultipliedLeading(1.5f));
                        }
                    }
                }
                System.out.println("Texto da petição adicionado com sucesso");
            } catch (Exception e) {
                System.err.println("Erro no texto da petição: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                System.out.println("Adicionando rodapé...");
//                inserirRodape(pdfDoc);
                System.out.println("Rodapé adicionado com sucesso");
            } catch (Exception e) {
                System.err.println("Erro no rodapé: " + e.getMessage());
                e.printStackTrace();
            }

            document.close();
            System.out.println("PDF gerado com sucesso");
            return baos.toByteArray();

        } catch (Exception e) {
            System.err.println("Erro geral ao gerar PDF: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }

    public void adicionarCapaProcesso(Document document, String capaTexto) throws IOException {
        PdfFont font = PdfFontFactory.createFont("fonts/arial.ttf", PdfEncodings.WINANSI);
        PdfFont bold = PdfFontFactory.createFont("fonts/arialbd.ttf", PdfEncodings.WINANSI);

        // 1. Inserir brasão da república
        try {
            var imageData = ImageDataFactory.create(new ClassPathResource("static/brasao_republica.png").getURL());
            Image brasao = new Image(imageData)
                    .scaleToFit(90, 90)
                    .setMarginTop(10)
                    .setMarginBottom(10)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(brasao);
        } catch (Exception e) {
            document.add(new Paragraph(" "));
        }

        String[] linhas = capaTexto.split("\\r?\\n");

        for (int i = 0; i < linhas.length; i++) {
            String linha = linhas[i].trim();

            if (linha.isEmpty()) {
                document.add(new Paragraph(" "));
                continue;
            }

            Paragraph par = new Paragraph(linha)
                    .setFont(font)
                    .setFontSize(10)
                    .setMultipliedLeading(1.2f)
                    .setMarginBottom(0);

            if (linha.equalsIgnoreCase("Poder Judiciário") ||
                    linha.equalsIgnoreCase("Justiça do Trabalho") ||
                    linha.equalsIgnoreCase("Tribunal Regional do Trabalho da 8ª Região")) {

                par.setFont(bold)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(-1);

            } else if (linha.startsWith("Ação Trabalhista")) {
                if ((i + 1) < linhas.length && linhas[i + 1].matches("^\\d{7}-\\d{2}\\.\\d{4}\\.5\\.08\\.\\d{4}$")) {
                    String numeroProcesso = linhas[i + 1];

                    Paragraph bloco = new Paragraph()
                            .add(new Text(linha + "\n").setFont(bold).setFontSize(20))
                            .add(new Text(numeroProcesso).setFont(bold).setFontSize(20))
                            .setTextAlignment(TextAlignment.CENTER)
                            .setMarginTop(10)
                            .setMarginBottom(10)
                            .setMultipliedLeading(1.0f);

                    document.add(bloco);
                    i++; // pula a linha do número do processo
                    continue;
                }

            } else if (linha.equalsIgnoreCase("Processo Judicial Eletrônico")) {
                par.setFont(bold)
                        .setFontSize(16)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(10)
                        .setMarginBottom(10);

            } else if (linha.startsWith("Data da Autuação:") || linha.startsWith("Valor da causa:")) {
                String[] partes = linha.split(":", 2);
                if (partes.length == 2) {
                    par = new Paragraph()
                            .add(new Text(partes[0] + ": ").setFont(bold))
                            .add(new Text(partes[1].trim()).setFont(font))
                            .setFontSize(12)
                            .setMarginBottom(-6);
                }

            } else if (linha.equalsIgnoreCase("Partes:")) {
                par.setFont(bold)
                        .setFontSize(12)
                        .setMarginTop(10);

            } else if (linha.startsWith("RECLAMANTE:") || linha.startsWith("RECLAMADO:")) {
                String[] partes = linha.split(":", 2);
                if (partes.length == 2) {
                    par = new Paragraph()
                            .add(new Text(partes[0] + ": ").setFont(bold))
                            .add(new Text(partes[1].trim()).setFont(font))
                            .setFontSize(12)
                            .setMarginLeft(15)
                            .setMarginBottom(-6);
                }

            } else if (linha.startsWith("ADVOGADO:")) {
                String[] partes = linha.split(":", 2);
                if (partes.length == 2) {
                    par = new Paragraph()
                            .add(new Text("ADVOGADO: "))
                            .add(new Text(partes[1].trim()).setFont(font))
                            .setFontSize(12)
                            .setMarginLeft(15)
                            .setMarginBottom(-6);
                }

            } else {
                par.setFont(font).setTextAlignment(TextAlignment.LEFT);
            }

            document.add(par);
        }

        // Quebra de página após capa
        document.add(new AreaBreak());
    }

    public void adicionarIntroPeticao(Document document, String vara, String textoIntro, PdfFont fontNormal, PdfFont fontNegrito) throws IOException {
        // 1. Logotipo (centralizado)
        try {
            var imageData = ImageDataFactory.create(new ClassPathResource("static/logo_ts_juridico.png").getURL());
            Image logo = new Image(imageData)
                    .scaleToFit(360, 160)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setMarginLeft(68)
                    .setMarginBottom(-20)
                    .setMarginTop(-65);
            document.add(logo);
        } catch (Exception e) {
            document.add(new Paragraph(" "));
        }

        Paragraph fls = new Paragraph("Fls.: 2")
                .setFont(fontNormal)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFixedPosition(460, 820, 100);
        document.add(fls);

        document.add(new Paragraph("\n"));

        Paragraph saudacao = new Paragraph("EXCELENTÍSSIMO (A) SENHOR (A) DOUTOR (A) JUIZ (A) DE DIREITO DA\n___ª VARA DO TRABALHO DE " + vara + ".")
                .setFont(fontNegrito)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setMultipliedLeading(1.2f)
                .setMarginLeft(50)
                .setMarginBottom(160);
        document.add(saudacao);

        // 4. Texto introdutório
        String[] linhas = textoIntro.split("\\r?\\n");
        for (String linha : linhas) {
            if (linha.trim().isEmpty()) {
                document.add(new Paragraph(" "));
                continue;
            }

            Paragraph par;

            // Aplica negrito apenas no nome do autor (1ª linha do parágrafo)
            if (linha.matches("^[A-Z ]+,.*")) {
                String[] partes = linha.split(",", 2);
                par = new Paragraph()
                        .add(new Text(partes[0] + ",").setFont(fontNegrito).setFontSize(12))
                        .add(new Text(partes[1]).setFont(fontNormal));
            } else {
                par = new Paragraph(linha).setFont(fontNormal);
            }

            par.setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFirstLineIndent(25)
                    .setMultipliedLeading(1.5f)
                    .setMarginLeft(50);

            document.add(par);
        }

        document.add(new Paragraph("\n"));
    }

    public void adicionarTextoReclamacao(Document document, String textoReclamacao, PdfFont fontNormal, PdfFont fontNegrito) throws IOException {
        // 1. Título centralizado
        Paragraph titulo = new Paragraph("RECLAMAÇÃO TRABALHISTA")
                .setFont(fontNegrito)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginLeft(28)
                .setMarginBottom(20);
        document.add(titulo);

        // 2. Texto justificado com recuo
        String[] partes = textoReclamacao.split(",", 2);
        String nomeEmpresa = partes[0].replace("Em face de ", "").trim();
        String restante = partes.length > 1 ? "," + partes[1] : "";

        Paragraph reclamacao = new Paragraph()
                .add(new Text("Em face de ").setFont(fontNormal))
                .add(new Text(nomeEmpresa).setFont(fontNegrito).setFontSize(12))
                .add(new Text(restante).setFont(fontNormal))
                .setFontSize(11)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFirstLineIndent(25)
                .setMultipliedLeading(1.5f)
                .setMarginLeft(50)
                .setMarginBottom(10);

        document.add(reclamacao);
    }

    public void adicionarTextoJusticaGratuita(Document document, String texto, PdfFont fontNormal, PdfFont fontNegrito) {
        Paragraph titulo = new Paragraph()
                .add(new Text("1. ").setFont(fontNegrito).setFontSize(12))
                .add(new Text(" DO PEDIDO DE JUSTIÇA GRATUITA")
                        .setFont(fontNegrito)
                        .setUnderline()
                        .setFontSize(12))
                .setMarginLeft(60)
                .setMarginTop(50);
        document.add(titulo);

        String[] partes = texto.split("\\[\\[JUSTIÇA GRATUITA]]");

        Paragraph corpo = new Paragraph()
                .add(new Text(partes[0]).setFont(fontNormal))
                .add(new Text("JUSTIÇA GRATUITA").setFont(fontNegrito).setUnderline())
                .add(new Text(partes[1]).setFont(fontNormal))
                .setFont(fontNormal)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFirstLineIndent(25)
                .setMultipliedLeading(1.5f)
                .setMarginTop(-4)
                .setMarginLeft(50)
                .setMarginBottom(10);

        document.add(corpo);
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
    }

    public void adicionarTextoHonorariosSucumbenciais(Document document, String texto, PdfFont fontNormal, PdfFont fontNegrito) {
        if (texto == null || texto.trim().isEmpty()) {
            return;
        }

        Paragraph titulo = new Paragraph()
                .add(new Text("2. ").setFont(fontNegrito).setFontSize(11))
                .add(new Text("DA CONDENAÇÃO DO RECLAMANTE AO PAGAMENTO DE HONORÁRIOS SUCUMBENCIAIS. A INCONSTITUCIONALIDADE DOS ARTS. 790-B, CAPUT E § 4º, E 791-A, § 4º DA CLT")
                        .setFont(fontNegrito)
                        .setUnderline()
                        .setFontSize(11))
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginLeft(60)
                .setMultipliedLeading(1.2f);
        document.add(titulo);

        String[] paragrafos = texto.split("\\r?\\n\\r?\\n");
        for (String par : paragrafos) {
            if (par.trim().isEmpty()) continue;

            String paragrafo = par.trim();

            if (paragrafo.startsWith("RECURSO DE REVISTA") ||
                    paragrafo.contains("RECURSO DE REVISTA – HONORÁRIOS SUCUMBENCIAIS")) {

                adicionarRecursoDeRevista(document, paragrafo, fontNormal, fontNegrito);

            } else {Paragraph p = new Paragraph(paragrafo)
                        .setFont(fontNormal)
                        .setFontSize(11)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFirstLineIndent(25)
                        .setMultipliedLeading(1.5f)
                        .setMarginLeft(50)
                        .setMarginBottom(-4);

                document.add(p);
            }
        }

        document.add(new Paragraph("\n"));
    }

    private void adicionarRecursoDeRevista(Document document, String texto, PdfFont fontNormal, PdfFont fontNegrito) {
        Table table = new Table(UnitValue.createPercentArray(1))
                .setWidth(UnitValue.createPercentValue(45))  // Mais estreita
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)  // Alinhada à direita
                .setMarginTop(10);

        Cell cell = new Cell();

        Paragraph cellParagraph = new Paragraph()
                .setFontSize(9)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setMultipliedLeading(1.1f)
                .setMargin(0);

        aplicarNegritoRecursoRevista(cellParagraph, texto, fontNormal, fontNegrito);

        cell.add(cellParagraph)
                .setBorder(Border.NO_BORDER)
                .setPadding(8)
                .setPaddingTop(5)
                .setPaddingBottom(5);

        table.addCell(cell);
        document.add(table);
    }

    private void aplicarNegritoRecursoRevista(Paragraph paragraph, String texto, PdfFont fontNormal, PdfFont fontNegrito) {
        // Trecho específico que deve ficar em negrito - versão mais curta para testar
        String trechoNegrito = "O Supremo Tribunal Federal";

        System.out.println("Procurando pelo trecho: " + trechoNegrito);
        System.out.println("Texto contém o trecho? " + texto.contains(trechoNegrito));

        if (texto.contains(trechoNegrito)) {
            System.out.println("ENCONTROU o trecho! Aplicando negrito...");

            int inicio = texto.indexOf(trechoNegrito);

            if (inicio > 0) {
                String antes = texto.substring(0, inicio);
                paragraph.add(new Text(antes).setFont(fontNormal));
                System.out.println("Adicionado texto antes: " + antes);
            }

            String trechoCompleto = "O Supremo Tribunal Federal, ao julgar a ADI 5.766/DF e afastar do ordenamento jurídico a previsão legal de cobrança de honorários sucumbenciais dos beneficiários da justiça gratuita, declarou a inconstitucionalidade dos arts. 790-B, caput e § 4º, e 791-A, § 4º, da CLT";

            int fimTrecho = texto.indexOf(trechoCompleto);
            if (fimTrecho >= 0) {
                String trechoParaNegrito = texto.substring(fimTrecho, fimTrecho + trechoCompleto.length());
                paragraph.add(new Text(trechoParaNegrito).setFont(fontNegrito));
                System.out.println("Adicionado texto em NEGRITO: " + trechoParaNegrito);

                if (fimTrecho + trechoCompleto.length() < texto.length()) {
                    String depois = texto.substring(fimTrecho + trechoCompleto.length());
                    paragraph.add(new Text(depois).setFont(fontNormal));
                    System.out.println("Adicionado texto depois: " + depois);
                }
            } else {
                paragraph.add(new Text(trechoNegrito).setFont(fontNegrito));
                System.out.println("Adicionado texto em NEGRITO (curto): " + trechoNegrito);

                String resto = texto.substring(inicio + trechoNegrito.length());
                paragraph.add(new Text(resto).setFont(fontNormal));
                System.out.println("Adicionado resto: " + resto);
            }
        } else {
            System.out.println("NÃO ENCONTROU o trecho. Usando fonte normal para tudo.");
            paragraph.add(new Text(texto).setFont(fontNormal));
        }
    }

    public void adicionarTextoInconstitucionalidade223G(Document document, String texto, PdfFont fontNormal, PdfFont fontNegrito) {
        Paragraph titulo = new Paragraph()
                .add(new Text("3. ").setFont(fontNegrito).setFontSize(12))
                .add(new Text("DA INCONSTITUCIONALIDADE DO ARTIGO 223-G DA CLT – ")
                        .setFont(fontNegrito).setUnderline())
                .add(new Text("ENTENDIMENTO PREDOMINANTE NO ÂMBITO DO TRT 8ª REGIÃO")
                        .setFont(fontNegrito).setUnderline())
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginLeft(50)
                .setMarginBottom(15);
        document.add(titulo);

        String[] paragrafos = texto.split("\\r?\\n\\r?\\n");
        for (String par : paragrafos) {
            if (par == null || par.trim().isEmpty()) continue;

            Paragraph p = new Paragraph(par.trim())
                    .setFont(fontNormal)
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setFirstLineIndent(25)
                    .setMultipliedLeading(1.5f)
                    .setMarginLeft(50)
                    .setMarginBottom(8);

            document.add(p);
        }

        document.add(new Paragraph("\n"));
    }

    private void inserirCabecalho(Document document) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 4}))
                .useAllAvailableWidth()
                .setBorderBottom(new SolidBorder(1));

        // Inserir logo (opcional)
        try {
            var imageData = ImageDataFactory.create(new ClassPathResource("static/logo_ts_juridico.png").getURL());
            Image logo = new Image(imageData).scaleToFit(60, 60);
            table.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
        } catch (Exception e) {
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        }

        // Nome e dados do escritório
        Paragraph headerText = new Paragraph("TS Jurídico\nAssessoria Trabalhista")
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(10)
                .setBold();
        table.addCell(new Cell().add(headerText).setBorder(Border.NO_BORDER));

        document.add(table);
        document.add(new Paragraph("\n")); // Espaço após cabeçalho
    }

    // Event Handler para rodapé automático
    private static class RodapeEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            int totalPages = pdfDoc.getNumberOfPages();

            try {
                PdfFont rodapeFont = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
                PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

                String rodapeText = String.format("Página %d de %d | TS Jurídico - Assessoria Trabalhista",
                        pageNumber, totalPages);

                canvas.beginText();
                canvas.setFontAndSize(rodapeFont, 9);
                canvas.setTextMatrix(85, 40);
                canvas.showText(rodapeText);
                canvas.endText();
                canvas.release();
            } catch (Exception e) {
                System.err.println("Erro no rodapé da página " + pageNumber + ": " + e.getMessage());
            }
        }
    }
}
