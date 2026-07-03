package com.emtap.mesapartes.service.mesapartes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Servicio de notificaciones por correo — Mesa de Partes Virtual.
 *
 * Flujo:
 * 1. Registro de expediente → enviarNotificacionMesaPartes()
 * FROM: tramite.documentario TO: mesadepartesvirtual
 * Template: templates/email/notificacion.html
 *
 * 2. Encargado hace clic en Aceptar/Rechazar → enviarRespuestaRemitente()
 * FROM: mesadepartesvirtual TO: correo del ciudadano
 * Template: templates/email/respuesta.html
 */
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender tramiteMailSender;
    private final JavaMailSender mesapartesMailSender;
    private final EmailTemplateLoader templateLoader;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.correo.tramite}")
    private String correoTramite;

    @Value("${app.correo.mesapartes}")
    private String correoMesapartes;

    @Value("${app.url-consulta}")
    private String URL_CONSULTA;

    public EmailService(
            @Qualifier("tramiteMailSender") JavaMailSender tramiteMailSender,
            @Qualifier("mesapartesMailSender") JavaMailSender mesapartesMailSender,
            EmailTemplateLoader templateLoader) {
        this.tramiteMailSender = tramiteMailSender;
        this.mesapartesMailSender = mesapartesMailSender;
        this.templateLoader = templateLoader;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Notificación de nuevo expediente → encargado de Mesa de Partes
    // ─────────────────────────────────────────────────────────────────────────

    @Async
    public void enviarNotificacionMesaPartes(
            String numExpediente, String nuEmi, String nuAnn,
            String nombreRemitente, String correoRemitente, String asunto, MultipartFile file) {

        try {
            String urlAceptar = baseUrl + "/api/public/expediente/" + nuAnn + "/" + nuEmi + "/aceptar";
            String urlRechazar = baseUrl + "/api/public/expediente/" + nuAnn + "/" + nuEmi + "/rechazar";

            String html = templateLoader.cargarSinEscape("notificacion.html", Map.of(
                    "NUM_EXPEDIENTE", numExpediente,
                    "NOMBRE_REMITENTE", nombreRemitente != null ? nombreRemitente : "",
                    "CORREO_REMITENTE", correoRemitente != null ? correoRemitente : "",
                    "ASUNTO", asunto != null ? asunto : "",
                    "URL_ACEPTAR", urlAceptar,
                    "URL_RECHAZAR", urlRechazar));

            MimeMessage message = tramiteMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(correoTramite);
            helper.setTo(correoMesapartes);
            helper.setSubject("📋 Nuevo Expediente Registrado: " + numExpediente);
            helper.setText(html, true);

            // Adjuntar el archivo subido por el ciudadano (si existe)
            if (file != null && !file.isEmpty()) {
                String nombreArchivo = file.getOriginalFilename() != null
                        ? file.getOriginalFilename()
                        : "adjunto";
                helper.addAttachment(nombreArchivo, new ByteArrayResource(file.getBytes()));
                log.info("Archivo adjunto '{}' incluido en la notificación de expediente {}", nombreArchivo,
                        numExpediente);
            }

            tramiteMailSender.send(message);
            log.info("Notificación de expediente {} enviada", numExpediente);

        } catch (Exception e) {
            log.error("Error enviando notificación para expediente {}: {}", numExpediente, e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Respuesta al ciudadano — aceptado o rechazado
    // ─────────────────────────────────────────────────────────────────────────

    @Async
    public void enviarRespuestaRemitente(String correoDestino, String numExpediente, boolean aceptado, String motivo) {

        if (correoDestino == null || correoDestino.isBlank()) {
            log.warn("Correo del ciudadano vacío para expediente {}", numExpediente);
            return;
        }

        try {
            String color = aceptado ? "#27ae60" : "#e74c3c";
            String icono = aceptado ? "✅" : "❌";
            String titulo = aceptado ? "Su expediente ha sido ACEPTADO" : "Su expediente ha sido RECHAZADO";
            String mensajeBase = aceptado
                    ? "Nos complace informarle que su expediente ha sido revisado y <strong>aceptado</strong> por la Mesa de Partes Virtual."
                    : "Lamentamos informarle que su expediente ha sido <strong>rechazado</strong> por la Mesa de Partes Virtual.";
            String motivoBloque = "";
            String plazoBloque = "";
            if (!aceptado && motivo != null && !motivo.isBlank()) {
                motivoBloque = "<div style='background:#fdf2f2;border-left:4px solid #e74c3c;padding:12px 16px;border-radius:4px;margin:16px 0;'>"
                        +
                        "<strong>Motivo del rechazo:</strong><br/>" + motivo + "</div>";
                plazoBloque = "<div style='background:#fef9e7;border:1px solid #f39c12;border-radius:6px;" +
                        "padding:12px 16px;margin:0 0 20px 0;font-size:13px;color:#7d6608;text-align:center;'>" +
                        "&#9200; <strong>Importante:</strong> Tiene <strong>2 d\u00edas h\u00e1biles</strong> " +
                        "para subsanar lo indicado en el motivo del rechazo.</div>";
            }
            String mensaje = mensajeBase;

            String html = templateLoader.cargarSinEscape("respuesta.html", Map.of(
                    "NUM_EXPEDIENTE", numExpediente,
                    "COLOR", color,
                    "ICONO", icono,
                    "TITULO", titulo,
                    "MENSAJE", mensaje,
                    "MOTIVO_BLOQUE", motivoBloque,
                    "PLAZO_BLOQUE", plazoBloque,
                    "URL_CONSULTA", URL_CONSULTA));

            MimeMessage message = mesapartesMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(correoMesapartes);
            helper.setTo(correoDestino);

            String estado = aceptado ? "ACEPTADO ✅" : "RECHAZADO ❌";
            helper.setSubject("Expediente " + numExpediente + " - " + estado);
            helper.setText(html, true);

            mesapartesMailSender.send(message);
            log.info("Respuesta ({}) enviada a {} para expediente {}", estado, correoDestino, numExpediente);

        } catch (Exception e) {
            log.error("Error enviando respuesta para expediente {}: {}", numExpediente, e.getMessage());
        }
    }
}
