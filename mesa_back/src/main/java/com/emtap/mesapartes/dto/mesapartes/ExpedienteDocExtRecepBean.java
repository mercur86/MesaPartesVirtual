package com.emtap.mesapartes.dto.mesapartes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteDocExtRecepBean {
    private String nuAnnExp;
    private String nuSecExp;
    private LocalDateTime feExp;
    private LocalDateTime feVence;
    private String coProceso;
    private String deDetalle;
    private String coDepEmi;
    private String nuCorrExp;
    private String nuExpediente;
    private Integer nuFolios;
    private Short nuPlazo;
    private String usCreaAudi;
    private String usModiAudi;
    private String esEstado;
    private String coTipoExp;
    private String clave;
}
