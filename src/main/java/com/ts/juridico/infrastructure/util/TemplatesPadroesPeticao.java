package com.ts.juridico.infrastructure.util;

import com.ts.juridico.application.dto.request.DadosPeticaoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TemplatesPadroesPeticao {


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
}
