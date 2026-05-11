package com.emtap.mesapartes.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tdtv_anexos", schema = "idosgd")
public class AnexosDoc {

    @EmbeddedId
    private AnexosDocId id;

    @Column(name = "bl_doc", columnDefinition = "bytea")
    private byte[] blDoc;

}
