package com.emtap.mesapartes.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tdtv_anexos", schema = "idosgd")
public class AnexosDoc {

    @EmbeddedId
    private AnexosDocId id;

    @Column(name = "bl_doc", columnDefinition = "bytea")
    private byte[] blDoc;

    @Column (name = "co_use_cre")
    private String userCreacion;

    @Column (name = "co_use_mod")
    private String userModificacion;

    @Column (name = "de_det")
    private String detalleNombre;

    @Column (name = "de_rut_ori")
    private String detalleRuta;

    @Column (name = "fe_use_mod")
    private LocalDateTime fechaUsuarioCreacion;

    @Column (name = "fe_use_cre")
    private LocalDateTime fechaUsuarioModificacion;

    @Column (name = "ti_public")
    private String publico;





}
