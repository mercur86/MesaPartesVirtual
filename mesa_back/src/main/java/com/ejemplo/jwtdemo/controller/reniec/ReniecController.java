package com.ejemplo.jwtdemo.controller.reniec;

import com.ejemplo.jwtdemo.dto.reniec.Persona;
import com.ejemplo.jwtdemo.entity.reniec.Ubigeo;
import com.ejemplo.jwtdemo.repository.reniec.UbigeoRepository;
import com.ejemplo.jwtdemo.service.listas.ListasCacheService;
import com.ejemplo.jwtdemo.service.reniec.ApiReniecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reniec")
public class ReniecController {
    @Autowired
    private  ApiReniecService apiReniecService;

    @Autowired
    private UbigeoRepository ubigeoRepository;

    @Autowired
    private  ListasCacheService  listasCacheService;

    @GetMapping("/buscar")
    public Mono<Persona> buscar(@RequestParam String dni) {
        return apiReniecService.obtenerDatos(dni);
    }


    @GetMapping("/listar")
    public ResponseEntity<Map<String, Object>> listarUbigeos(@RequestParam(required = false) String q) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Ubigeo> lista = listasCacheService.getListaUbigeos();
            if (q != null && !q.trim().isEmpty()) {
                String query = q.trim().toLowerCase();
                lista = lista.stream()
                        .filter(u -> (u.getNombreCompleto() != null && u.getNombreCompleto().toLowerCase().contains(query)))
                        .toList();
            }
            response.put("status", "success");
            response.put("total", lista.size());
            response.put("data", lista);
            response.put("timestamp", new Date());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "No se pudo obtener la lista de ubigeos");
            response.put("timestamp", new Date());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/refrescar")
    public String refrescar() {
        listasCacheService.refrescar();
        return "✅ Catálogos refrescados correctamente";
    }

}
