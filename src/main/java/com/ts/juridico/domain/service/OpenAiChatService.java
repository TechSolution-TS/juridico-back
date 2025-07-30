package com.ts.juridico.domain.service;

import com.google.gson.Gson;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.ts.juridico.application.dto.request.DadosChatClienteRequestDto;
import com.ts.juridico.application.dto.request.DadosPeticaoRequestDto;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.port.OpenAiPort;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.tika.Tika;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OpenAiChatService {

    private final RestTemplate rest;
    private final Tika tika;
    private final OpenAiPort openAiPort;
    private final Gson gson = new Gson();

    public String summaryPetitionChat(ArquivoModeloPeticao arquivoModeloPeticao) {
        try {
            String text = rest.execute(
                    arquivoModeloPeticao.getLinkDownload(),
                    HttpMethod.GET,
                    null,
                    clientHttpResponse -> {
                        try (InputStream in = clientHttpResponse.getBody()) {
                            try {
                                return tika.parseToString(in);
                            } catch (TikaException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage("system",
                    "Você é um assistente jurídico. Sempre que receber o texto de uma petição, responda com um resumo de até 10 linhas, seguindo o formato: " +
                            "1. Identificação das Partes\n2. Objeto da Petição\n3. Fundamentação Jurídica\n4. Pedidos\n5. Observações Finais"));
            messages.add(new ChatMessage("user", text));

            return applyBoldHtml(openAiPort.summaryPetition(messages));
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair texto: " + e.getMessage(), e);
        }
    }

    public String juridicoChat(String textoSolicitado) {
        try {
            ChatMessage systemMessage = new ChatMessage("system",
                    "Você é um assistente jurídico inteligente e confiável. Responda com clareza, objetividade e linguagem profissional. " +
                            "Você pode realizar tarefas como: 1) Melhorar a redação de textos jurídicos; 2) Tirar dúvidas sobre Direito; " +
                            "3) Explicar expressões jurídicas; 4) Sugerir argumentos legais; 5) Corrigir gramática em textos jurídicos; " +
                            "6) Resumir conteúdos legais ou petições. Sempre responda com base no contexto fornecido pelo usuário."
            );

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(systemMessage);
            messages.add(new ChatMessage("user", textoSolicitado));
            return openAiPort.summaryPetition(messages);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair texto: " + e.getMessage(), e);
        }
    }

    public String chatCliente(DadosChatClienteRequestDto dados) {
        try {
            String clienteInfo = dados.getClienteInfo();
            String pergunta = dados.getSolicitacao();

            ChatMessage systemMessage = new ChatMessage("system", getClientSystemPrompt());

            String contextoCompleto = buildClientContext(clienteInfo, pergunta);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(systemMessage);
            messages.add(new ChatMessage("user", contextoCompleto));

            return openAiPort.summaryPetition(messages);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao processar pergunta do cliente: " + e.getMessage(), e);
        }
    }

    public String gerarPeticaoCompleta(DadosPeticaoRequestDto dados) {
        // ESTRATÉGIA: Tentar primeiro com sistema que sabemos que funciona

        System.out.println("🔥 ESTRATÉGIA 1: Usando formato que funcionou no treinamento");
        String resultado = tentarFormatoOriginal(dados);

        // Se retornou template, processar manualmente
        if (resultado.contains("${config.") || resultado.contains("${{")) {
            System.out.println("⚡ ESTRATÉGIA 2: Processando template manualmente");
            return processarTemplateManualmente(resultado, dados);
        }

        return resultado;
    }

    private String tentarFormatoOriginal(DadosPeticaoRequestDto dados) {
        // Usar EXATAMENTE o mesmo formato do exemplo que funcionou no treinamento
        String systemMessage = "Você é um advogado trabalhista do escritório TS Jurídico. Sua função é gerar petições trabalhistas COMPLETAS seguindo rigorosamente a estrutura padrão do escritório: 1) Identificação do advogado, 2) Vocativo, 3) Qualificação das partes, 4) Título, 5) Pedidos preliminares, 6) Fatos, 7) Mérito, 8) Pedidos finais, 9) Valor da causa, 10) Encerramento.";

        String prompt = String.format(
                "DADOS DO CLIENTE: Nome: %s, %s, %s, %s, RG %s, CPF %s, %s. " +
                        "DADOS DA EMPRESA: %s, CNPJ %s, %s. " +
                        "DADOS CONTRATUAIS: Admissão: %s, Rescisão: %s, Função: %s, Salário: %s, Jornada: %s, CTPS não assinada, Regime: %s. " +
                        "PEDIDOS: %s. VARA: %s%s",

                dados.getNomeCliente(), dados.getNacionalidade(), dados.getEstadoCivil(),
                dados.getProfissao(), dados.getRg(), dados.getCpf(), dados.getEnderecoCliente(),
                dados.getRazaoSocial(), dados.getCnpj(), dados.getEnderecoEmpresa(),
                dados.getDataAdmissao(), dados.getDataRescisao(), dados.getFuncao(),
                dados.getSalario(), dados.getJornada(), dados.getRegime(),
                String.join(", ", dados.getPedidos()), dados.getVara(),
                dados.getValorCausa() != null ? ", VALOR DA CAUSA: " + dados.getValorCausa() : ""
        );

        System.out.println("=== PROMPT FORMATO ORIGINAL ===");
        System.out.println("System: " + systemMessage);
        System.out.println("User: " + prompt);
        System.out.println("===============================");

        return openAiPort.generatePetition(systemMessage, prompt);
    }

    private String processarTemplateManualmente(String template, DadosPeticaoRequestDto dados) {
        System.out.println("🔧 Processando template manualmente com dados reais...");

        try {
            // Substituir placeholders com dados reais
            String resultado = template;

            // Identificação do advogado
            String identificacaoAdvogado = "Winnie Souza – OAB/PA 18.113\n" +
                    "Telefone: (91) 98507-7302 / Winnie_souza@yahoo.com.br\n" +
                    "Edifício Torre Vitta Office, sala 1912";

            // Vocativo
            String vocativo = "EXCELENTÍSSIMO (A) SENHOR (A) DOUTOR (A) JUIZ (A) DE DIREITO DA MM VARA DO TRABALHO DE " +
                    dados.getVara().toUpperCase() + "/PA – TRT8";

            // Qualificação das partes - SEM String.format para evitar erro de conversão
            StringBuilder qualificacaoReclamante = new StringBuilder();
            qualificacaoReclamante.append(dados.getNomeCliente().toUpperCase())
                    .append(", ").append(dados.getNacionalidade())
                    .append(", ").append(dados.getEstadoCivil())
                    .append(", ").append(dados.getProfissao())
                    .append(", portador do RG nº ").append(dados.getRg())
                    .append(" e inscrito no CPF sob o nº ").append(dados.getCpf())
                    .append(", residente e domiciliado na ").append(dados.getEnderecoCliente())
                    .append(", vem, respeitosamente, por intermédio de sua advogada que ao final assina, propor:\n\n")
                    .append("RECLAMAÇÃO TRABALHISTA\n\n")
                    .append("Em face de ").append(dados.getRazaoSocial().toUpperCase())
                    .append(", pessoa jurídica de direito privado, inscrita no CNPJ sob o n° ")
                    .append(dados.getCnpj())
                    .append(", estabelecida na ").append(dados.getEnderecoEmpresa())
                    .append(", tendo por base as razões de fato e de direito a seguir expostas:");

            // Pedidos preliminares padrão
            String pedidosPreliminares = "1. DO PEDIDO DE JUSTIÇA GRATUITA\n" +
                    "O Reclamante se declara pobre, nos termos do art. 4º da lei 1.060/50. Assim sendo, uma vez impossibilitado de arcar com as custas e/ou despesas processuais, requer respeitosamente, com fulcro no artigo 5º, LXXIV, da Constituição Federal, combinado como artigo 98 do Código de Processo Civil Brasileiro, que Vossa Excelência, conceda-lhe os benefícios da JUSTIÇA GRATUITA, isentando-o de quaisquer pagamentos.\n\n" +

                    "2. DA CONDENAÇÃO DO RECLAMANTE AO PAGAMENTO DE HONORÁRIOS SUCUMBENCIAIS. A INCONSTITUCIONALIDADE DOS ARTS. 790-B, CAPUT E § 4º, E 791-A, § 4º, DA CLT\n" +
                    "Sendo deferido o benefício da justiça gratuita para o Reclamante, o que assim se espera, visto que o referido é hipossuficiente, pleiteia-se pela sua não condenação ao pagamento de honorários sucumbenciais. Excelência, temos como incontroversa a decisão do Supremo Tribunal Federal, ao julgar a ADI 5.766/DF e afastar do ordenamento jurídico a previsão legal de cobrança de honorários sucumbenciais dos beneficiários da justiça gratuita, declarando a inconstitucionalidade dos arts. 790-B, caput e § 4º, e 791-A, § 4º, da CLT.\n\n" +

                    "3. DO JUÍZO 100% DIGITAL\n" +
                    "A Resolução 345/2020 do CNJ, trouxe ao Judiciário a implementação do Juízo 100% Digital, da qual, a partir da Resolução 034/2021 deste E. TRT8, houve a adesão deste Regional. Assim, requer a tramitação dos presentes autos pelo Juízo 100% Digital.";

            // Pedidos finais
            String pedidosFinais = gerarPedidosFinais(dados);

            // Valor da causa
            String valorCausa = dados.getValorCausa() != null ?
                    "Dá-se à causa o valor de " + dados.getValorCausa() + "." : "";

            // Encerramento
            StringBuilder encerramento = new StringBuilder();
            encerramento.append("Nestes termos,\nPede e espera deferimento.\n\n")
                    .append(dados.getVara()).append("/PA, ___/___/2025.\n\n")
                    .append("WINNIE DE FÁTIMA O. SOUZA\nOAB/PA Nº 18.113");

            // Aplicar substituições de forma segura
            resultado = resultado.replace("${config.PARTE_ADVOGADO}", identificacaoAdvogado);
            resultado = resultado.replace("${config.PARTE_VOCATIVO}", vocativo);
            resultado = resultado.replace("${config.PARTE_QUALIFICACAO}", qualificacaoReclamante.toString());
            resultado = resultado.replace("${config.PARTE_TITULO}", "");
            resultado = resultado.replace("${config.PARTE_PEDIDOS_PRELIMINARES}", pedidosPreliminares);
            resultado = resultado.replace("${config.PARTE_PEDIDOS_FINAIS}", pedidosFinais);
            resultado = resultado.replace("${config.PARTE_VALOR_CAUSA}", valorCausa);
            resultado = resultado.replace("${config.PARTE_ENCERRAMENTO}", encerramento.toString());

            // Limpar qualquer placeholder restante
            resultado = resultado.replaceAll("\\$\\{config\\.[^}]+\\}", "");

            // Limpar múltiplas quebras de linha
            resultado = resultado.replaceAll("\\n{3,}", "\n\n");

            System.out.println("✅ Template processado com sucesso!");
            return resultado;

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar template: " + e.getMessage());
            e.printStackTrace();

            // Em caso de erro, retornar uma petição básica funcional
            return gerarPeticaoBasica(dados);
        }
    }

    private String gerarPeticaoBasica(DadosPeticaoRequestDto dados) {
        System.out.println("🛡️ Gerando petição básica como fallback...");

        StringBuilder peticao = new StringBuilder();

        // Cabeçalho
        peticao.append("Winnie Souza – OAB/PA 18.113\n")
                .append("Telefone: (91) 98507-7302 / Winnie_souza@yahoo.com.br\n")
                .append("Edifício Torre Vitta Office, sala 1912\n\n");

        // Vocativo
        peticao.append("EXCELENTÍSSIMO (A) SENHOR (A) DOUTOR (A) JUIZ (A) DE DIREITO DA MM VARA DO TRABALHO DE ")
                .append(dados.getVara().toUpperCase()).append("/PA – TRT8\n\n");

        // Qualificação
        peticao.append(dados.getNomeCliente().toUpperCase())
                .append(", ").append(dados.getNacionalidade())
                .append(", ").append(dados.getEstadoCivil())
                .append(", ").append(dados.getProfissao())
                .append(", portador do RG nº ").append(dados.getRg())
                .append(" e inscrito no CPF sob o nº ").append(dados.getCpf())
                .append(", residente e domiciliado na ").append(dados.getEnderecoCliente())
                .append(", vem, respeitosamente, por intermédio de sua advogada que ao final assina, propor:\n\n");

        // Título
        peticao.append("RECLAMAÇÃO TRABALHISTA\n\n");

        // Empresa
        peticao.append("Em face de ").append(dados.getRazaoSocial().toUpperCase())
                .append(", pessoa jurídica de direito privado, inscrita no CNPJ sob o n° ")
                .append(dados.getCnpj())
                .append(", estabelecida na ").append(dados.getEnderecoEmpresa())
                .append(", tendo por base as razões de fato e de direito a seguir expostas:\n\n");

        // Fatos básicos
        peticao.append("DOS FATOS\n\n")
                .append("O Reclamante foi admitido em ").append(dados.getDataAdmissao())
                .append(" para exercer a função de ").append(dados.getFuncao())
                .append(", percebendo salário de ").append(dados.getSalario())
                .append(". A relação de trabalho foi encerrada em ").append(dados.getDataRescisao())
                .append(", sem o devido pagamento das verbas rescisórias.\n\n");

        // Pedidos
        peticao.append("DOS PEDIDOS\n\n")
                .append("Requer-se a condenação da Reclamada ao pagamento de:\n");

        for (int i = 0; i < dados.getPedidos().size(); i++) {
            peticao.append(numeroRomano(i + 1)).append(". ")
                    .append(dados.getPedidos().get(i).toUpperCase())
                    .append(";\n");
        }

        // Valor da causa
        if (dados.getValorCausa() != null) {
            peticao.append("\nDá-se à causa o valor de ").append(dados.getValorCausa()).append(".\n\n");
        }

        // Encerramento
        peticao.append("Nestes termos,\nPede e espera deferimento.\n\n")
                .append(dados.getVara()).append("/PA, ___/___/2025.\n\n")
                .append("WINNIE DE FÁTIMA O. SOUZA\nOAB/PA Nº 18.113");

        return peticao.toString();
    }

    private String gerarPedidosFinais(DadosPeticaoRequestDto dados) {
        try {
            StringBuilder pedidos = new StringBuilder();
            pedidos.append("DOS PEDIDOS\n");
            pedidos.append("Diante do exposto, requer-se que Vossa Excelência se digne julgar totalmente procedente a presente Reclamação trabalhista, condenando a Reclamada ao pagamento de:\n");

            pedidos.append("I. OS BENEFÍCIOS DA GRATUIDADE DA JUSTIÇA...........................ILÍQUIDO;\n");
            pedidos.append("II. TRAMITAÇÃO PELO JUÍZO 100% DIGITAL...............................ILÍQUIDO;\n");

            int contador = 3;
            for (String pedido : dados.getPedidos()) {
                String itemPedido = converterPedidoParaItem(pedido);
                pedidos.append(numeroRomano(contador)).append(". ").append(itemPedido).append(";\n");
                contador++;
            }

            pedidos.append(numeroRomano(contador)).append(". JUROS E CORREÇÃO MONETÁRIA.......................................ILÍQUIDO.");

            return pedidos.toString();

        } catch (Exception e) {
            System.err.println("❌ Erro ao gerar pedidos finais: " + e.getMessage());
            // Fallback simples
            StringBuilder fallback = new StringBuilder();
            fallback.append("DOS PEDIDOS\n");
            fallback.append("Requer-se a condenação da Reclamada ao pagamento de:\n");

            for (int i = 0; i < dados.getPedidos().size(); i++) {
                fallback.append(numeroRomano(i + 1)).append(". ")
                        .append(dados.getPedidos().get(i).toUpperCase()).append(";\n");
            }

            return fallback.toString();
        }
    }

    private String converterPedidoParaItem(String pedido) {
        switch (pedido.toLowerCase()) {
            case "rescisão indireta":
                return "RESCISÃO INDIRETA DO CONTRATO DE TRABALHO..........ILÍQUIDO";
            case "adicional noturno":
                return "ADICIONAL NOTURNO 20%......................................................ILÍQUIDO";
            case "horas extras":
                return "HORAS EXTRAS 50%................................................................ILÍQUIDO";
            case "fgts + 40%":
                return "FGTS 8% + MULTA 40%........................................................ILÍQUIDO";
            case "verbas rescisórias":
                return "VERBAS RESCISÓRIAS........................................................ILÍQUIDO";
            case "dano moral":
                return "DANO MORAL..........................................................................ILÍQUIDO";
            case "trct":
                return "ENTREGA DE TRCT E PPP.....................................................ILÍQUIDO";
            default:
                return pedido.toUpperCase() + "..........................................................ILÍQUIDO";
        }
    }

    private String numeroRomano(int numero) {
        String[] romanos = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"};
        return numero <= romanos.length ? romanos[numero - 1] : String.valueOf(numero);
    }

    private String applyBoldHtml(String texto) {
        return texto.replaceAll("(?m)^(\\d+\\. )\\*\\*(.*?)\\*\\*", "<strong>$1$2</strong>");
    }

    public String testarModeloComExemploConhecido() {
        String systemMessage = "Você é um advogado trabalhista do escritório TS Jurídico. Sua função é gerar petições trabalhistas COMPLETAS seguindo rigorosamente a estrutura padrão do escritório: 1) Identificação do advogado, 2) Vocativo, 3) Qualificação das partes, 4) Título, 5) Pedidos preliminares, 6) Fatos, 7) Mérito, 8) Pedidos finais, 9) Valor da causa, 10) Encerramento.";

        String prompt = "DADOS DO CLIENTE: Nome: Paulo Ricardo Silva, brasileiro, divorciado, porteiro, RG 9876543 PC/PA, CPF 456.789.123-99, Rua Nova Esperança, 789, Jaderlândia, CEP 68700-020, Capanema/PA. DADOS DA EMPRESA: Condomínio Residencial Bela Vista, CNPJ 11.222.333/0001-44, Rua das Acácias, 200, Centro, CEP 68700-001, Capanema/PA. DADOS CONTRATUAIS: Admissão: 05/06/2021, Rescisão: 20/11/2024, Função: porteiro, Salário: R$ 1.580,00, Jornada: 22h às 06h, CTPS não assinada, Regime: 6x1. PEDIDOS: rescisão indireta, adicional noturno, horas extras, FGTS + 40%, verbas rescisórias, dano moral, TRCT. VARA: Capanema/PA";

        return openAiPort.generatePetition(systemMessage, prompt);
    }

    private String getClientSystemPrompt() {
        return "Você é um assistente de atendimento ao cliente de um escritório de advocacia especializado em Direito do Trabalho. " +
                "Responda como se fosse um atendente experiente, cordial e profissional.\n\n" +

                "INSTRUÇÕES IMPORTANTES:\n" +
                "1. Use linguagem clara, amigável e sem jargões jurídicos excessivos\n" +
                "2. Seja empático e compreensivo com as preocupações do cliente\n" +
                "3. Sempre base suas respostas nas informações específicas do processo do cliente\n" +
                "4. Se não souber algo específico, seja honesto e sugira contato direto com o advogado\n" +
                "5. Mantenha um tom profissional mas caloroso\n" +
                "6. Organize respostas longas em tópicos para facilitar a leitura\n" +
                "7. Explique termos jurídicos quando necessário\n" +
                "8. Foque em tranquilizar o cliente e fornecer informações úteis\n\n" +

                "TIPOS DE RESPOSTA:\n" +
                "- Para perguntas sobre STATUS: explique o que significa o status atual e próximos passos\n" +
                "- Para perguntas sobre PRAZOS: informe prazos relevantes e sua importância\n" +
                "- Para perguntas sobre DOCUMENTOS: liste o que pode ser necessário\n" +
                "- Para perguntas sobre VALORES: explique de forma geral sem dar valores específicos\n" +
                "- Para perguntas FORA do escopo jurídico: redirecione educadamente para o processo\n\n" +

                "FORMATO DA RESPOSTA:\n" +
                "- Comece sempre cumprimentando o cliente pelo nome\n" +
                "- Use emojis moderadamente para humanizar (😊, 📋, ⏰, etc.)\n" +
                "- Termine sempre oferecendo ajuda adicional\n" +
                "- Se necessário, forneça informações de contato do escritório";
    }

    private String buildClientContext(String clienteInfoJson, String pergunta) {
        try {
            StringBuilder contexto = new StringBuilder();
            contexto.append("CONTEXTO DO ATENDIMENTO:\n");
            contexto.append("O cliente está fazendo a seguinte pergunta sobre seu processo: ").append(pergunta).append("\n\n");
            contexto.append("DADOS DO CLIENTE E PROCESSO(S):\n");
            contexto.append(clienteInfoJson).append("\n\n");
            contexto.append("INSTRUÇÕES PARA RESPOSTA:\n");
            contexto.append("- Use as informações específicas do processo para responder\n");
            contexto.append("- Seja claro sobre o status atual do processo\n");
            contexto.append("- Explique próximos passos quando relevante\n");
            contexto.append("- Se a pergunta não estiver relacionada ao processo, oriente educadamente\n");
            contexto.append("- Mantenha tom profissional mas amigável\n");
            contexto.append("- Cumprimente o cliente pelo primeiro nome\n");

            return contexto.toString();

        } catch (Exception e) {
            return "O cliente " + " está perguntando: " + pergunta +
                    "\nDados do processo: " + clienteInfoJson +
                    "\nResponda de forma amigável e profissional baseado nas informações disponíveis.";
        }
    }

    private String formatarStatusParaCliente(String status) {
        switch (status.toLowerCase()) {
            case "em-andamento":
                return "Em Andamento - Seu processo está sendo acompanhado ativamente";
            case "prazo-para-resposta":
                return "Aguardando Prazo - Há um prazo em curso que está sendo observado";
            case "conclusos-para-julgamento":
                return "Aguardando Julgamento - Seu processo está na fila para decisão do juiz";
            case "arquivado":
                return "Arquivado - Processo foi finalizado";
            case "pendente-protocolo":
                return "Pendente de Protocolo - Documentos sendo preparados para envio";
            default:
                return status;
        }
    }
}