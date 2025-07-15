package com.ts.juridico.application.dto.response;

import lombok.Data;

@Data
public class PeticaoResponseDto {
    private boolean sucesso;
    private String mensagem;
    private String peticao;
    private String vara;
    private String textoIntro;
    private String capa;
    private String textoReclamacao;
    private String textoJusticaGratuita;
    private String textoHonorariosSucumbenciais;
    private String textoInconstitucionalidade223G;
}