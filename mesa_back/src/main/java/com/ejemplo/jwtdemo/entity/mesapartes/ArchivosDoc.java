package com.ejemplo.jwtdemo.entity.mesapartes;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tdtv_archivo_doc", schema = "idosgd")
public class ArchivosDoc {

    @EmbeddedId
    private ArchivoDocId id;


    @Column(name = "bl_doc", columnDefinition = "bytea")
    private byte[] blDoc;

    @Column(name = "de_ruta_origen", length = 400)
    private String rutaOrigen;

    @Column(name = "de_ruta_destino", length = 400)
    private String rutaDestino;

    @Column(name = "es_firma", length = 1)
    private String esFirma; // '0' o '1'


    @Column(name = "bl_doc_edit",columnDefinition = "bytea")
    private byte[] blDocEdit;

    @Column(name = "feula", length = 8)
    private String feUla;


    @Column(name = "w_bl_doc", columnDefinition = "bytea")
    private byte[] wBlDoc;

    @Column(name = "w_de_ruta_origen", length = 400)
    private String wRutaOrigen;

    /** RELACIÓN hacia REMITOS **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "nu_ann", referencedColumnName = "nu_ann", insertable = false, updatable = false),
            @JoinColumn(name = "nu_emi", referencedColumnName = "nu_emi", insertable = false, updatable = false)
    })
    private Remito remito;

}
