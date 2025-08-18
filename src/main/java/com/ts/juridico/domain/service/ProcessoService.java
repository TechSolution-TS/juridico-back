package com.ts.juridico.domain.service;

import com.ts.juridico.domain.model.InfoProcessoUsuario;
import com.ts.juridico.domain.model.Processo;
import com.ts.juridico.domain.port.InfoProcessoUsuarioPort;
import com.ts.juridico.domain.port.ProcessoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoService {

    private final ProcessoPort processoPort;
    private final InfoProcessoUsuarioPort infoProcessoUsuarioPort;

    @Transactional
    public Processo saveProcess(Long userId, String summary) {
        InfoProcessoUsuario infoProcessUser = infoProcessoUsuarioPort.findInfoProcessUser(userId);
        return processoPort.saveProcess(userId, infoProcessUser.getAdvogadoResponsavel(), summary, infoProcessUser.getTribunal(), infoProcessUser.getProcessoUuid());
    }

    public List<Processo> findAll() {
        return processoPort.findAll();
    }

    public Processo findByProcessUuid(String processUuid) {
        return processoPort.findByProcessUuid(processUuid);
    }

    @Transactional
    public Processo save(Processo processo) {
        return processoPort.save(processo);
    }

    public List<Processo> findByUserId(Long userId) {
        return processoPort.findByUserId(userId);
    }

    public List<Processo> findByAdvogado(String adv) {
        return processoPort.findByAdvogado(adv);
    }
}
