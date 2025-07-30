package com.ts.juridico.infrastructure.util;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.ts.juridico.application.dto.request.DadosPeticaoRequestDto;
import com.ts.juridico.domain.port.OpenAiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class TemplatesPadroesPeticao {

    private final OpenAiPort openAiPort;

    public String generateTextPetitionChat(String descricao) {
        try {
            List<ChatMessage> messages = List.of(
                    new ChatMessage("system", """
                            Você é um assistente jurídico especialista em Direito do Trabalho.
                            Com base na descrição fornecida pelo usuário sobre as atividades desempenhadas pelo trabalhador e sua exposição a agentes insalubres, gere um tópico completo de petição.
                                    
                            Siga **exatamente** as orientações abaixo:
                            1. Título do tópico de petição, todo em letras maiúsculas, iniciando com "DA FUNÇÃO DE ..." e finalizando com a natureza jurídica do pedido (ex: INSALUBRIDADE, EXPOSIÇÃO A AGENTES QUÍMICOS, ACÚMULO DE FUNÇÃO, etc). Este é um exemplo 'DA FUNÇÃO DE SERVIÇOS GERAIS, GARÇONETE E COZINHEIRA – INSALUBRIDADE'
                            2. Escreva de forma técnica, argumentativa, respeitosa e persuasiva, em tom de petição trabalhista.
                            3. Utilize **pelo menos 4 parágrafos** com estrutura lógica: introdução, fatos, fundamentos jurídicos e conclusão.
                            4. Utilize expressões jurídicas e referências à legislação trabalhista e normas de segurança do trabalho (como NR-15).
                            5. Ressalte a habitualidade das funções, o acúmulo de tarefas, a exposição a agentes nocivos e a omissão do empregador.
                            6. Use o pronome de tratamento **"Excelência"** ao se dirigir ao juízo.
                            7. Ao final, indique a necessidade de adicional de insalubridade, reconhecimento do vínculo e reparação dos danos.
                            8. Não adicione assinatura, local, data, OAB, ou expressões como 'Termos em que...'.
                                           
                            Gere o titulo logo no inicio
                            """), new ChatMessage("user", descricao)
            );

            return openAiPort.summaryPetition(messages);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair texto: " + e.getMessage(), e);
        }
    }

    public String generateTextDoMerito(String descricao, List<String> motivosRescisaoAlineas) {
        try {
            String alineaFormatada = formatarAlineas(motivosRescisaoAlineas);

            String prompt = """
                Você é um assistente jurídico. Gere uma introdução técnica para o tópico 'DO MÉRITO', baseada na descrição fornecida. 
                Use linguagem clara, objetiva, e inclua menção ao artigo 483 da CLT e às alíneas correspondentes.
                - Abaixo um exemplo:
                Consoante aos fatos trazidos ao norte desta inicial, tem-se como inquestionável o descumprimento do contrato de trabalho por parte da reclamada, em razão da ausência de assinatura da CLT, descaso na forma de tratamento com seus funcionários e expor a trabalhadora em cargos diferentes do seu e sem ganhar a mais por isso. 
                Assim, Excelência, resta claro que a reclamada deixou de cumprir com suas obrigações contratuais, nesse sentido, vejamos o disposto no artigo 483, alínea “a”, “c” e “d” da CLT expressamente dispõe:
                
                - Texto deve ser encerrado com a frase: '..., vejamos o disposto no artigo 483, alínea %s da CLT expressamente dispõe:'
                
                - Descrição: %s
                """.formatted(alineaFormatada, descricao);

            List<ChatMessage> messages = List.of(
                    new ChatMessage("system", prompt),
                    new ChatMessage("user", descricao)
            );
            return openAiPort.summaryPetition(messages);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair texto: " + e.getMessage(), e);
        }
    }

    public String gerarCapaProcesso(DadosPeticaoRequestDto dados) {
        StringBuilder capa = new StringBuilder();

        String numeroProcesso = dados.getNumeroProcesso();
        String dataAutuacao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String valorCausa = dados.getValorCausa();

        capa.append("Poder Judiciário\n");
        capa.append("Justiça do Trabalho\n");
        capa.append("Tribunal Regional do Trabalho da 8ª Região\n\n");
        capa.append("Ação Trabalhista - Rito Ordinário\n");
        capa.append(numeroProcesso).append("\n\n");
        capa.append("Processo Judicial Eletrônico\n\n");
        capa.append("Data da Autuação: ").append(dataAutuacao).append("\n");
        capa.append("Valor da causa: ").append(valorCausa).append("\n\n");
        capa.append("Partes:\n");

        // Reclamante
        capa.append("RECLAMANTE: ").append(dados.getNomeCliente().toUpperCase()).append("\n");
        if (dados.getAdvogadosReclamante() != null && !dados.getAdvogadosReclamante().isEmpty()) {
            for (String advogado : dados.getAdvogadosReclamante()) {
                capa.append("ADVOGADO: ").append(advogado.toUpperCase()).append("\n");
            }
        }

        // Reclamado
        capa.append("RECLAMADO: ").append(dados.getRazaoSocial().toUpperCase()).append("\n");
        if (dados.getAdvogadosReclamado() != null && !dados.getAdvogadosReclamado().isEmpty()) {
            for (String advogado : dados.getAdvogadosReclamado()) {
                capa.append("ADVOGADO: ").append(advogado.toUpperCase()).append("\n");
            }
        }

        return capa.toString();
    }

    public String gerarTextoIntro(DadosPeticaoRequestDto dto) {
        String nome = dto.getNomeCliente();
        String nacionalidade = dto.getNacionalidade();
        String estadoCivil = dto.getEstadoCivil();
        String profissao = dto.getProfissao();
        String rg = dto.getRg();
        String cpf = dto.getCpf();
        String endereco = dto.getEnderecoCliente();

        // Usa o campo 'sexo' para determinar os pronomes
        boolean feminino = "F".equalsIgnoreCase(dto.getSexo());
        String artigo = feminino ? "a" : "";
        String partResidencia = feminino ? "a" : "o";

        String advogadoSaudacao;
        if (dto.getAdvogadosReclamante().size() == 1) {
            advogadoSaudacao = feminino
                    ? "por intermédio de sua advogada que ao final assina"
                    : "por intermédio de seu advogado que ao final assina";
        } else {
            advogadoSaudacao = "através dos Advogados signatários, habilitados conforme Instrumento Particular de Procuração anexo";
        }

        return String.format(
                "%s, %s, %s, %s, portador%s do RG nº %s e CPF nº %s, residente e domiciliad%s na %s, vem, respeitosamente, %s, propor:",
                nome.toUpperCase(),
                nacionalidade,
                estadoCivil,
                profissao,
                artigo,
                rg,
                cpf,
                partResidencia,
                endereco,
                advogadoSaudacao
        );
    }

    public String gerarTextoReclamacao(DadosPeticaoRequestDto dto) {
        String razaoSocial = dto.getRazaoSocial();
        String cnpj = dto.getCnpj();
        String endereco = dto.getEnderecoEmpresa();

        return String.format(
                "Em face de %s, pessoa jurídica de direito privado, inscrita no CNPJ sob o nº %s, estabelecida na %s, tendo por base as razões de fato e de direito a seguir expostas:",
                razaoSocial.toUpperCase(),
                cnpj,
                endereco
        );
    }

    public String gerarTextoJusticaGratuita(DadosPeticaoRequestDto dto) {
        boolean feminino = "F".equalsIgnoreCase(dto.getSexo());
        String genero = feminino ? "A Reclamante" : "O Reclamante";
        String pronome = feminino ? "a" : "o";

        return genero + " se declara pobre, nos termos do art. 4º da lei 1.060/50. Assim sendo, " +
                "uma vez impossibilitad" + pronome + " de arcar com as custas e/ou despesas processuais, " +
                "requer respeitosamente, com fulcro no artigo 5º, LXXIV, da Constituição Federal, combinado " +
                "com artigo 98 do Código de Processo Civil Brasileiro, que Vossa Excelência, conceda-lhe os " +
                "benefícios da [[JUSTIÇA GRATUITA]], isentando-" + pronome + " de quaisquer pagamentos.";
    }

    public String gerarTextoHonorariosSucumbenciais(DadosPeticaoRequestDto dto) {
        boolean feminino = "F".equalsIgnoreCase(dto.getSexo());
        String genero = feminino ? "à Reclamante" : "ao Reclamante";
        String pronome = feminino ? "a" : "o";

        return "Sendo deferido o benefício da justiça gratuita " + genero + ", o que assim se espera, visto que "
                + (feminino ? "a" : "o") + " referida parte é hipossuficiente, pleiteia-se pela sua não condenação ao pagamento de honorários sucumbenciais.\n\n"
                + "Nesse sentido, verifica-se a seguinte jurisprudência do E. TST:\n\n"
                + "RECURSO DE REVISTA – HONORÁRIOS SUCUMBENCIAIS RECLAMANTE BENEFICIÁRIA DA JUSTIÇA GRATUITA – CONDENAÇÃO INDEVIDA – "
                + "RECLAMAÇÃO TRABALHISTA AJUIZADA NA VIGÊNCIA DA LEI Nº 13.467/2017 – DIVERGÊNCIA JURISPRUDENCIAL. 1. O art. 5º, LXXIV, da Constituição "
                + "Federal preceitua que o Estado prestará assistência jurídica integral e gratuita aos que comprovarem insuficiência de recursos. "
                + "2. O Supremo Tribunal Federal, ao julgar a ADI 5.766/DF e afastar do ordenamento jurídico a previsão legal de cobrança de honorários sucumbenciais "
                + "dos beneficiários da justiça gratuita, declarou a inconstitucionalidade dos arts. 790-B, caput e § 4º, e 791-A, § 4º, da CLT, assegurando "
                + "o cumprimento de direito fundamental do trabalhador elencado no inciso LXXIV do art. 5º da Constituição da República. 3. Assim, a condenação "
                + "imposta nas instâncias ordinárias à reclamante ao pagamento de honorários sucumbenciais diverge de outros Tribunais Regionais, que entendem "
                + "que a beneficiária da justiça gratuita não pode ser compelida à condenação ao pagamento da mencionada verba. 4. Ademais, a decisão regional "
                + "se encontra centrada no fundamento de constitucionalidade tem eficácia contra todos e efeito vinculante, nos termos do § 2º do art. 102 da "
                + "Constituição Federal, devendo ser observada em âmbito administrativo e judicial. Recurso de revista conhecido e provido. "
                + "(TST - RR: 100630720185020017, Relator: Margarida Rodrigues Costa, Data de Julgamento: 06/04/2022, 2ª Turma, Data de Publicação: 08/04/2022)\n\n"
                + "Excelência, temos como incontroversa a decisão do Supremo Tribunal Federal, ao julgar a ADI 5.766/DF e afastar do ordenamento jurídico "
                + "a previsão legal de cobrança de honorários sucumbenciais dos beneficiários da justiça gratuita, declarando a inconstitucionalidade dos "
                + "arts. 790-B, caput e § 4º, e 791-A, § 4º, da CLT, que assegurou o cumprimento de direito fundamental do trabalhador elencado no inciso LXXIV "
                + "do art. 5º da Constituição da República.\n\n"
                + "Logo, em observância ao ordenamento legal vigente, requer-se que em eventual indeferimento dos pedidos aqui requeridos, seja " + genero
                + " isento" + (feminino ? "a" : "") + " do pagamento de honorários sucumbenciais.";
    }

    public String gerarTextoInconstitucionalidade223G() {
        return "Em 2017 entrou em vigor a Lei nº 13.467/2017, que teve por escopo a reforma da " +
                "Consolidação das Leis Trabalhistas, assim, temos a inclusão do art. 223-G, que passou a " +
                "normatizar o arbitramento da reparação por danos morais.\n\n" +

                "A norma elencada legisla a respeito da reparação de danos de natureza " +
                "extrapatrimonial oriundos das relações de trabalho, pautando o dano diretamente à " +
                "remuneração do Ofendido, logo, subjugando aquele que possuía menor vencimento em " +
                "detrimento ao de maior remuneração.\n\n" +

                "Não obstante, em 14 de setembro de 2020, o Egrégio Tribunal Regional do Trabalho " +
                "da 8ª Região, em sua composição plenária, declarou a inconstitucionalidade do art. 223-G, " +
                "§ 1º, I a IV, da CLT.\n\n" +

                "Corolário da acertada decisão do TRT8, permitir a tarifação do dano moral " +
                "caracteriza óbvia e direta ofensa ao que dispõe o art. 5º da CF/1988, especialmente porque " +
                "impõe limites à fixação da indenização viola princípios básicos da nossa Carta Magna, e " +
                "princípios constitucionais, como o da Dignidade da Pessoa Humana e o da Isonomia.\n\n" +

                "Sendo assim, porquanto ofensivo aos princípios básicos da nossa Constituição, deve " +
                "ser declarada/ratificada a inconstitucionalidade do art. 223-G e seguintes da CLT quando " +
                "do julgamento deste processo.";
    }

    public String gerarTextoContratoTrabalho(DadosPeticaoRequestDto dto) {
        boolean feminino = "F".equalsIgnoreCase(dto.getSexo());
        String artigo = feminino ? "A" : "O";
        String obreiro = feminino ? "Obreira" : "Obreiro";
        String pronome = feminino ? "da" : "do";
        String reclamante = feminino ? "Reclamante" : "Reclamante";
        String municipio = dto.getMunicipioEmpresa();;

        return String.format(
                "%s %s foi admitid%s em %s para exercer a função de %s, com suas atividades desenvolvidas no município de %s. " +
                        "Desde a admissão pela Reclamada, %s recebia seu salário mensalmente. " +
                        "O último salário %s %s foi de %s.\n" +
                        "O regime de trabalho %s %s era de %s, com jornada das %s. " +
                        "A relação de trabalho foi encerrada em %s, sem o pagamento da remuneração proporcional e das verbas rescisórias.",
                artigo, reclamante,
                feminino ? "a" : "o",
                dto.getDataAdmissao(),
                dto.getFuncao(),
                municipio,
                obreiro,
                pronome,
                reclamante.toLowerCase(),
                dto.getSalario(),
                pronome,
                reclamante.toLowerCase(),
                dto.getRegime(),
                dto.getJornada(),
                dto.getDataRescisao()
        );
    }

    public String gerarTextoCtpDifJornada(DadosPeticaoRequestDto dto) {
        boolean incluirTopico = dto.isTemHorasExtras() || dto.isTemIntervaloInterjornada() || dto.isTemCtpsAssinada();

        if (!incluirTopico) return null;

        boolean feminino = "F".equalsIgnoreCase(dto.getSexo());
        String artigo = feminino ? "A" : "O";
        String obreiro = feminino ? "Obreira" : "Obreiro";
        String pronome = feminino ? "da" : "do";

        return artigo + " Reclamante, desde sua admissão, prestava serviços para a Reclamada de forma habitual, onerosa, pessoal e contínua, "
                + "desempenhando com zelo e dedicação suas atividades na função de " + dto.getFuncao().toLowerCase() + ". "
                + "Apesar de todo o vínculo empregatício existente, sua CTPS jamais foi assinada, situação que demonstra a evidente tentativa da Reclamada "
                + "de se eximir de suas obrigações trabalhistas, em total afronta à legislação vigente.\n"
                + "Ao longo da relação contratual, mesmo exercendo a função para a qual fora contratado, o Reclamante não teve respeitado o piso salarial "
                + "estabelecido pela Convenção Coletiva da Categoria. Os reajustes, promoções e eventuais progressões salariais foram solenemente ignorados, "
                + "gerando prejuízos financeiros consideráveis.\n"
                + "Além disso, a jornada de trabalho cumprida extrapolava os limites legais. "
                + artigo + " Reclamante laborava das " + dto.getJornada() + ", sob regime de " + dto.getRegime() + ", frequentemente realizando horas extras "
                + "sem qualquer contraprestação ou compensação, tampouco usufruía do intervalo intrajornada legalmente previsto. "
                + "Fica, portanto, demonstrado que, além da omissão quanto ao vínculo formal, a Reclamada desrespeitou garantias mínimas relativas à jornada de trabalho, "
                + "remuneração adequada e condições dignas de labor.";
    }

    public List<String> gerarTextoDoMerito(DadosPeticaoRequestDto dto) {
        StringBuilder titulo = new StringBuilder();
        List<String> alineas = dto.getMotivosRescisaoAlineas();

        titulo.append("DA RESCISÃO INDIRETA DO CONTRATO DE TRABALHO - ");
        titulo.append("NÃO CUMPRIMENTO DE OBRIGAÇÕES DO CONTRATO DE TRABALHO ");
        titulo.append("POR PARTE DA RECLAMADA - ART. 483");

        if (alineas != null && !alineas.isEmpty()) {
            titulo.append(", ALÍNEA");
            if (alineas.size() > 1) {
                titulo.append("S ");
            } else {
                titulo.append(" ");
            }

            // Concatena as alíneas com vírgulas e "e" no final
            for (int i = 0; i < alineas.size(); i++) {
                titulo.append("“").append(alineas.get(i).toUpperCase()).append("”");
                if (i < alineas.size() - 2) {
                    titulo.append(", ");
                } else if (i == alineas.size() - 2) {
                    titulo.append(" e ");
                }
            }

            titulo.append(" DA CLT.");
        } else {
            titulo.append(".");
        }

        String tituloDoMerito = titulo.toString();
        String textoInicialDomerito = this.generateTextDoMerito(dto.getDescricaoFuncaoServico(), dto.getMotivosRescisaoAlineas());

        Map<String, String> artigo483Descricoes = Map.of(
                "A", "a) forem exigidos serviços superiores às suas forças, defesos por lei, contrários aos bons costumes, ou alheios ao contrato;",
                "B", "b) for tratado pelo empregador ou por seus superiores hierárquicos com rigor excessivo;",
                "C", "c) correr perigo manifesto de mal considerável;",
                "D", "d) não cumprir o empregador as obrigações do contrato;"
        );

        StringBuilder artigo483 = new StringBuilder();
        artigo483.append("Art. 483. O empregado poderá considerar rescindido o contrato e pleitear a devida indenização quando:\n");

        if (alineas != null) {
            for (String alinea : alineas) {
                String descricao = artigo483Descricoes.get(alinea.toUpperCase());
                if (descricao != null) {
                    artigo483.append(descricao).append("\n");
                }
            }
        }

        String genero = "F".equalsIgnoreCase(dto.getSexo()) ? "a Reclamante" : "o Reclamante";
        String artigo = (alineas.size() == 1) ? "alínea " : "alíneas ";
        StringBuilder alineaFormatada = new StringBuilder();
        for (int i = 0; i < alineas.size(); i++) {
            alineaFormatada.append("“").append(alineas.get(i).toLowerCase()).append("”");
            if (i < alineas.size() - 2) {
                alineaFormatada.append(", ");
            } else if (i == alineas.size() - 2) {
                alineaFormatada.append(" e ");
            }
        }

        String textoFinal = String.format(
                "Desta forma, por estar comprovada a perfeita aplicação ao disposto no art. 483, %s%s, CLT, %s vem a este Juízo postular que seja declarada a rescisão indireta do seu contrato de trabalho, condenando assim a reclamada ao pagamento das verbas rescisórias que lhe são devidas.",
                artigo, alineaFormatada.toString(), genero
        );

        List<String> doMerito = new ArrayList<>();
        doMerito.add(tituloDoMerito);
        doMerito.add(textoInicialDomerito);
        doMerito.add(artigo483.toString());
        doMerito.add(textoFinal);

        return doMerito;
    }

    private String formatarAlineas(List<String> alineaList) {
        if (alineaList == null || alineaList.isEmpty()) return "";

        if (alineaList.size() == 1) return "“" + alineaList.get(0) + "”";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alineaList.size(); i++) {
            sb.append("“").append(alineaList.get(i)).append("”");
            if (i < alineaList.size() - 2) sb.append(", ");
            else if (i == alineaList.size() - 2) sb.append(" e ");
        }
        return sb.toString();
    }
}
