package com.ejemplo.jwtdemo.controller;


import com.ejemplo.jwtdemo.dto.sunat.EmpresaSunat;
import com.ejemplo.jwtdemo.service.sunat.ApiSunatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private ApiSunatService apiSunatService;


    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("uptime", "Sistema funcionando correctamente");

        return ResponseEntity.ok(health);
    }

    @GetMapping("/buscar")
    public Mono<EmpresaSunat> buscar(@RequestParam String ruc)
    {
        return apiSunatService.obtenerDatos(ruc);
    }


}