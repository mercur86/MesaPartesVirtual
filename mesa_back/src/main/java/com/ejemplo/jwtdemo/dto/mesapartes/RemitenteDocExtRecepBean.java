package com.ejemplo.jwtdemo.dto.mesapartes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemitenteDocExtRecepBean {
    private String coLocEmi;
    private String coEmpEmi;
    private String coEmpRes;
    private String nuDni;
    private String nuRuc;
    private String coOtros;
    private String coDep;
    private String idDepartamento;
    private String idProvincia;
    private String idDistrito;
    private String deDireccion;
    private String nuDniRes;
    private String deNuDniRes;
    
    @NotBlank(message = "El correo es obligatorio")
    private String deCorreo;
    
    private String telefono;
    private String deCargo;
    private String coOtrosRes;
    private String deNomOtrosRes;
    private String coComision;
    private String coTipoCongresista;
    private String coTipoInv;
    private String reiterativo;
    private String notificado;
    private String sensible;
    private String emiResp;
}