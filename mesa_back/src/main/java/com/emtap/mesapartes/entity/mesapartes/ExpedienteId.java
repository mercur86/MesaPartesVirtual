package com.emtap.mesapartes.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteId implements Serializable {

    @Column(name = "nu_ann_exp", length = 4, nullable = false)
    private String anioExpediente;

    @Column(name = "nu_sec_exp", length = 10, nullable = false)
    private String secuenciaExpediente;
}
