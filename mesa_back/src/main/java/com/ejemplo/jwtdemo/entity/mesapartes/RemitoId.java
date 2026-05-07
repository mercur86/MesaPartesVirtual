package com.ejemplo.jwtdemo.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemitoId implements Serializable {

    @Column(name = "nu_ann", length = 4, nullable = false)
    private String anio;

    @Column(name = "nu_emi", length = 10, nullable = false)
    private String numeroEmision;
}