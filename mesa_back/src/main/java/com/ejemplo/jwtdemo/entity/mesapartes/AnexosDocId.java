package com.ejemplo.jwtdemo.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AnexosDocId implements Serializable {

    @Column(name = "nu_ann", length = 4)
    private String nuAnn;

    @Column(name = "nu_emi", length = 10)
    private String nuEmi;

    @Column(name = "nu_ane")
    private Integer nuAne;
}
