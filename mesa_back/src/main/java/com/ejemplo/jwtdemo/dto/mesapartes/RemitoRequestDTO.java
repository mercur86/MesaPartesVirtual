package com.ejemplo.jwtdemo.dto.mesapartes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RemitoRequestDTO {
    
    @Valid
    @NotNull(message = "Los datos del documento son obligatorios")
    private DocumentoExtRecepBean documento;
    
    private ExpedienteDocExtRecepBean expediente;
    
    @Valid
    @NotNull(message = "Los datos del remitente son obligatorios")
    private RemitenteDocExtRecepBean remitente;
}