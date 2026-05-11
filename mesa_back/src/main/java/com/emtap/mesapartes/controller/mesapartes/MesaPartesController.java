package com.emtap.mesapartes.controller.mesapartes;

import com.emtap.mesapartes.dto.mesapartes.RemitoRequestDTO;
import com.emtap.mesapartes.entity.mesapartes.TipoDocumento;
import com.emtap.mesapartes.repository.mesapartes.TipoDocumentoRepository;
import com.emtap.mesapartes.service.mesapartes.ExpedienteDecisionService;
import com.emtap.mesapartes.service.mesapartes.ExpedienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/public")
public class MesaPartesController {

    private final ExpedienteService expedienteService;
    private final ExpedienteDecisionService expedienteDecisionService;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public MesaPartesController(
            ExpedienteService expedienteService,
            ExpedienteDecisionService expedienteDecisionService,
            TipoDocumentoRepository tipoDocumentoRepository) {
        this.expedienteService = expedienteService;
        this.expedienteDecisionService = expedienteDecisionService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @GetMapping("/listaTipoDocumento")
    public ResponseEntity<Map<String, Object>> listaTipoDocumento() {
        List<TipoDocumento> lista = tipoDocumentoRepository.findByCdocIndbajNot("1");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("total", lista.size());
        response.put("data", lista);
        response.put("timestamp", new Date());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/expediente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> insertarExpediente(
            @Valid @RequestPart("mpv") RemitoRequestDTO mpv,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "anexos", required = false) List<MultipartFile> anexos) {

        log.info("Solicitud de inserción de expediente: {}", mpv);

        Map<String, Object> response = new HashMap<>();

        try {
            String rmitoNumEmi = expedienteService.procesarExpedienteCompleto(mpv, file, anexos);
            response.put("objeto", rmitoNumEmi);
            response.put("status", "success");
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error al insertar expediente", e);
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoints Aceptar / Rechazar Expediente

    @GetMapping(value = "/expediente/{nuAnn}/{nuEmi}/aceptar", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> aceptarExpediente(
            @PathVariable String nuAnn, @PathVariable String nuEmi) {
        String html = expedienteDecisionService.procesarAceptacion(nuAnn, nuEmi);
        return ResponseEntity.ok(html);
    }

    @GetMapping(value = "/expediente/{nuAnn}/{nuEmi}/rechazar", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> formularioRechazo(
            @PathVariable String nuAnn, @PathVariable String nuEmi) {
        String html = expedienteDecisionService.generarFormularioRechazo(nuAnn, nuEmi);
        return ResponseEntity.ok(html);
    }

    @PostMapping(value = "/expediente/{nuAnn}/{nuEmi}/rechazar", 
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> rechazarExpediente(
            @PathVariable String nuAnn,
            @PathVariable String nuEmi,
            @RequestParam(value = "motivo", required = false) String motivo) {
        String html = expedienteDecisionService.procesarRechazo(nuAnn, nuEmi, motivo);
        return ResponseEntity.ok(html);
    }
}