package com.ejemplo.jwtdemo.service.mesapartes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utilidad para cargar y procesar plantillas HTML de correo.
 *
 * Las plantillas se ubican en: src/main/resources/templates/email/
 * Las variables se definen como {{NOMBRE_VARIABLE}} dentro del HTML.
 *
 * Uso:
 * String html = emailTemplateLoader.cargar("notificacion.html", Map.of(
 * "NUM_EXPEDIENTE", "2026-0000001",
 * "ASUNTO", "Solicitud de conexión"
 * ));
 */
@Slf4j
@Component
public class EmailTemplateLoader {

    private static final String TEMPLATE_BASE = "templates/email/";

    /**
     * Carga una plantilla HTML y reemplaza todas las variables {{CLAVE}} por sus
     * valores.
     *
     * @param nombreArchivo nombre del archivo, p.ej. "notificacion.html"
     * @param variables     mapa de clave → valor para reemplazar
     * @return HTML resultante listo para enviar
     */
    public String cargar(String nombreArchivo, Map<String, String> variables) {
        String ruta = TEMPLATE_BASE + nombreArchivo;
        try {
            ClassPathResource resource = new ClassPathResource(ruta);
            byte[] bytes = resource.getInputStream().readAllBytes();
            String contenido = new String(bytes, StandardCharsets.UTF_8);

            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String valor = entry.getValue() != null ? entry.getValue() : "";
                contenido = contenido.replace(placeholder, escaparHtml(valor));
            }
            return contenido;

        } catch (IOException e) {
            log.error("No se pudo cargar la plantilla de correo '{}': {}", ruta, e.getMessage());
            // Fallback mínimo para no perder el envío
            return "<p>Error cargando plantilla. Expediente referenciado: "
                    + variables.getOrDefault("NUM_EXPEDIENTE", "?") + "</p>";
        }
    }

    /**
     * Escapa caracteres HTML para evitar inyección en los valores de las variables.
     * Las URLs (que deben ir en href) NO deben escaparse — pasa la URL sin escapar
     * si la asignas directamente a atributos href en la plantilla.
     */
    private String escaparHtml(String texto) {
        if (texto == null)
            return "";
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /**
     * Versión sin escape HTML — usar para variables que ya son HTML o URLs.
     */
    public String cargarSinEscape(String nombreArchivo, Map<String, String> variables) {
        String ruta = TEMPLATE_BASE + nombreArchivo;
        try {
            ClassPathResource resource = new ClassPathResource(ruta);
            byte[] bytes = resource.getInputStream().readAllBytes();
            String contenido = new String(bytes, StandardCharsets.UTF_8);

            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                String valor = entry.getValue() != null ? entry.getValue() : "";
                contenido = contenido.replace(placeholder, valor);
            }
            return contenido;

        } catch (IOException e) {
            log.error("No se pudo cargar la plantilla de correo '{}': {}", ruta, e.getMessage());
            return "<p>Error cargando plantilla.</p>";
        }
    }
}
