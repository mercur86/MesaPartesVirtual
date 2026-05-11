package com.emtap.mesapartes.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(schema = "idosgd", name = "si_mae_tipo_doc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento {
    @Id
    @Column(name = "cdoc_tipdoc", length = 3, nullable = false)
    private String cdocTipdoc;

    @Column(name = "cdoc_desdoc", length = 100)
    private String cdocDesdoc;

    @Column(name = "cdoc_indbaj", length = 1)
    private String cdocIndbaj;

    @Column(name = "fdoc_fecbaj")
    private LocalDateTime fdocFecbaj;

    @Column(name = "cdoc_grupo", length = 2)
    private String cdocGrupo;

    @Column(name = "in_numeracion", length = 1)
    private String inNumeracion;

    @Column(name = "in_tipo_firma", length = 1)
    private String inTipoFirma;

    @Column(name = "in_doc_salida", length = 1)
    private String inDocSalida;

    @Column(name = "in_multiple", length = 1)
    private String inMultiple = "0";
}
