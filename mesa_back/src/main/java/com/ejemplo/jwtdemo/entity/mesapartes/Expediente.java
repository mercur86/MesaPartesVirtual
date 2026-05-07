package com.ejemplo.jwtdemo.entity.mesapartes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(schema = "idosgd", name = "tdtc_expediente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expediente implements Serializable {

    @EmbeddedId
    private ExpedienteId id;

    @Column(name = "fe_exp")
    private LocalDateTime fechaExpediente;

    @Column(name = "fe_vence")
    private LocalDateTime fechaVencimiento;

    @Column(name = "co_proceso", length = 4)
    private String codigoProceso;

    @Column(name = "de_detalle", length = 400)
    private String detalle;

    @Column(name = "co_dep_exp", length = 5)
    private String codigoDependencia;

    @Column(name = "co_gru", length = 1)
    private String codigoGrupo;

    @Column(name = "nu_corr_exp", length = 7)
    private String numeroCorrelativo;

    @Column(name = "nu_expediente", length = 70)
    private String numeroExpediente;

    @Column(name = "nu_folios")
    private Integer numeroFolios;

    @Column(name = "nu_plazo")
    private Short plazo;

    @Column(name = "us_crea_audi", length = 10)
    private String usuarioCrea;

    @Column(name = "fe_crea_audi")
    private LocalDateTime fechaCrea;

    @Column(name = "us_modi_audi", length = 10)
    private String usuarioModifica;

    @Column(name = "fe_modi_audi")
    private LocalDateTime fechaModifica;

    @Column(name = "es_estado", length = 1)
    private String estado;

    @Column(name = "ti_rec_mp", length = 1)
    private String tipoRecepcionMesaPartes;

    @Column(name = "ccod_tipo_exp", length = 2)
    private String codigoTipoExpediente;

    @Column(name = "cclave", length = 4)
    private String clave;

    @Column(name = "nu_cuo", length = 10)
    private String numeroCuo;
}
