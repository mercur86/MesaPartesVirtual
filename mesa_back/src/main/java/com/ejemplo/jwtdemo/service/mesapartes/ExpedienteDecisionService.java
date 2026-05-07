package com.ejemplo.jwtdemo.service.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.Expediente;
import com.ejemplo.jwtdemo.entity.mesapartes.ExpedienteId;
import com.ejemplo.jwtdemo.entity.mesapartes.Remito;
import com.ejemplo.jwtdemo.repository.mesapartes.ExpedienteRepository;
import com.ejemplo.jwtdemo.repository.mesapartes.RemitoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ExpedienteDecisionService {

    private final RemitoRepository remitoRepository;
    private final ExpedienteRepository expedienteRepository;
    private final EmailService emailService;
    private final EmailTemplateLoader templateLoader;

    public ExpedienteDecisionService(
            RemitoRepository remitoRepository,
            ExpedienteRepository expedienteRepository,
            EmailService emailService,
            EmailTemplateLoader templateLoader) {
        this.remitoRepository = remitoRepository;
        this.expedienteRepository = expedienteRepository;
        this.emailService = emailService;
        this.templateLoader = templateLoader;
    }

    @Transactional
    public String procesarAceptacion(String nuAnn, String nuEmi) {
        return procesarDecision(nuAnn, nuEmi, true, null);
    }

    @Transactional
    public String procesarRechazo(String nuAnn, String nuEmi, String motivo) {
        return procesarDecision(nuAnn, nuEmi, false, motivo);
    }

    private String procesarDecision(String nuAnn, String nuEmi, boolean aceptado, String motivo) {
        String icono = aceptado ? "✅" : "❌";
        String estadoTexto = aceptado ? "ACEPTADO" : "RECHAZADO";
        String color = aceptado ? "#27ae60" : "#e74c3c";

        try {
            // 1. Buscar el Remito por año de emisión y número de emisión
            Optional<Remito> remitoOpt = remitoRepository.findByAnioAndNumeroEmision(nuAnn, nuEmi);
            if (remitoOpt.isEmpty()) {
                log.warn("No se encontró remito nuAnn={} nuEmi={}", nuAnn, nuEmi);
                return generarPaginaHtml("⚠️ Expediente no encontrado",
                        "No se encontró el expediente indicado.", "#e67e22");
            }

            Remito remito = remitoOpt.get();
            String correoRemitente = remito.getCorreoExpediente();

            // 2. Buscar el Expediente usando anioExpediente + secuenciaExpediente del Remito
            String anioExp = remito.getAnioExpediente();
            String secExp = remito.getSecuenciaExpediente();

            String numExpediente;
            if (anioExp != null && secExp != null) {
                Optional<Expediente> expedienteOpt = expedienteRepository.findById(new ExpedienteId(anioExp, secExp));
                numExpediente = expedienteOpt
                        .map(Expediente::getNumeroExpediente)
                        .orElse(anioExp + "-" + secExp);
            } else {
                numExpediente = nuAnn + "-" + nuEmi;
            }

            // 3. Enviar correo al ciudadano
            emailService.enviarRespuestaRemitente(correoRemitente, numExpediente, aceptado, motivo);
            log.info("Expediente {} {} — correo enviado a: {}", numExpediente, estadoTexto, correoRemitente);

            // 4. Generar respuesta HTML
            String detalle = "El expediente <strong>" + numExpediente + "</strong> ha sido <strong>"
                    + estadoTexto + "</strong>.<br/>Se ha notificado al ciudadano por correo electrónico.";
            if (!aceptado && motivo != null && !motivo.isBlank()) {
                detalle += "<br/><br/><em>Motivo registrado:</em> " + motivo;
            }

            return generarPaginaHtml(icono + " Expediente " + estadoTexto, detalle, color);

        } catch (Exception e) {
            log.error("Error al procesar decisión para nuEmi={}: {}", nuEmi, e.getMessage());
            return generarPaginaHtml("⚠️ Error", "Ocurrió un error al procesar la solicitud.", "#e74c3c");
        }
    }

    public String generarFormularioRechazo(String nuAnn, String nuEmi) {
        String actionUrl = "/api/public/expediente/" + nuAnn + "/" + nuEmi + "/rechazar";
        return templateLoader.cargarSinEscape("formulario-rechazo.html", Map.of(
                "ACTION_URL", actionUrl));
    }

    private String generarPaginaHtml(String titulo, String mensaje, String color) {
        return templateLoader.cargarSinEscape("decision.html", Map.of(
                "TITULO", titulo,
                "MENSAJE", mensaje,
                "COLOR", color));
    }
}