package com.ejemplo.jwtdemo.dto.sunat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaSunat {
    private String numero_documento;
    private String razon_social;
    private String nombre_comercial;
    private String estado;
    private String condicion;
    private String direccion;
    private String ubigeo;
    private String departamento;
    private String provincia;
    private String distrito;
}
