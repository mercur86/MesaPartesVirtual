package com.emtap.mesapartes.entity.mesapartes;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ArchivoDocId implements Serializable {

    @Column(name = "nu_ann", length = 4)
    private String nuAnn;

    @Column(name = "nu_emi", length = 10)
    private String nuEmi;
}
