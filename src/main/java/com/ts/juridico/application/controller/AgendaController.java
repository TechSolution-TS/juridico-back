package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.AgendaRequestDto;
import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.application.dto.response.AgendaResponseDto;
import com.ts.juridico.domain.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping("/agendar")
    public ResponseEntity<?> createFolder(@RequestBody AgendaRequestDto dto) {
        try {
            agendaService.createAgenda(dto);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao tentar criar um agendamento", e);
        }

        return ResponseEntity.ok("Operação realiza com sucesso!");
    }

    @GetMapping()
    public ResponseEntity<List<AgendaResponseDto>> searchAppointments() {
        try {
            return ResponseEntity.ok(agendaService.searchAppointments());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao tentar buscar agendamentos", e);
        }
    }

    @DeleteMapping("/{agendamentoId}")
    public ResponseEntity<String> deleteAppointments(@PathVariable Long agendamentoId) {
        try {
            agendaService.deleteAppointments(agendamentoId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao tentar buscar agendamentos", e);
        }

        return ResponseEntity.ok("Operação realiza com sucesso!");
    }
}
