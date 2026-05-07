package com.ejemplo.jwtdemo.entity.mesapartes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity

@Table(
        schema = "idosgd",
        name = "tdtv_remitos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nu_ann", "co_dep_emi", "ti_emi", "co_tip_doc_adm", "nu_cor_doc", "nu_doc_emi"}),
                @UniqueConstraint(columnNames = {"nu_ann", "co_dep_emi", "co_gru", "nu_cor_emi"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remito {

    @EmbeddedId
    private RemitoId id;

    @Column(name = "nu_cor_emi")
    private Long numeroCorrelativoEmision;

    @Column(name = "co_loc_emi", length = 3, nullable = false)
    private String codigoLocalEmision;

    @Column(name = "co_dep_emi", length = 5)
    private String codigoDependenciaEmision;

    @Column(name = "ti_emi", length = 2)
    private String tipoEmision;

    @Column(name = "nu_dni_emi", length = 8)
    private String dniEmisor;

    @Column(name = "co_emp_emi", length = 5)
    private String codigoEmpleadoEmisor;

    @Column(name = "nu_ruc_emi", length = 11)
    private String rucEmisor;

    @Column(name = "co_otr_ori_emi", length = 10)
    private String otroOrigenEmisor;

    @Column(name = "fe_emi")
    private LocalDateTime fechaEmision;

    @Column(name = "nu_doc")
    private Long numeroDocumento;

    @Column(name = "co_gru", length = 1)
    private String codigoGrupo;

    @Column(name = "de_asu", length = 1000)
    private String asunto;

    @Column(name = "es_doc_emi", length = 1, nullable = false)
    private String estadoDocumentoEmision;

    @Column(name = "nu_dia_ate")
    private Short diasAtencion;

    @Column(name = "de_obs", length = 200)
    private String observaciones;

    @Column(name = "fe_arc")
    private LocalDateTime fechaArchivo;

    @Column(name = "ti_cap", length = 2)
    private String tipoCap;

    @Column(name = "co_exp", length = 5)
    private String codigoExpediente;

    @Column(name = "es_eli", length = 1, nullable = false)
    private String eliminado;

    @Column(name = "co_use_cre", length = 20, nullable = false)
    private String usuarioCrea;

    @Column(name = "fe_use_cre", nullable = false)
    private LocalDateTime fechaCrea;

    @Column(name = "co_use_mod", length = 20, nullable = false)
    private String usuarioModifica;

    @Column(name = "fe_use_mod", nullable = false)
    private LocalDateTime fechaModifica;

    @Column(name = "co_tip_doc_adm", length = 3)
    private String tipoDocumentoAdm;

    @Column(name = "co_tip_doc_pro", length = 3)
    private String tipoDocumentoPro;

    @Column(name = "de_ane", length = 200)
    private String anexo;

    @Column(name = "co_loc_emi_pro", length = 6)
    private String localEmisionPro;

    @Column(name = "es_upd", length = 1)
    private String actualizado;

    @Column(name = "co_emp_res", length = 5)
    private String codigoEmpleadoResponsable;

    @Column(name = "nu_candes")
    private Short cantidadDestinatarios;

    @Column(name = "nu_cor_doc")
    private Integer correlativoDocumento;

    @Column(name = "nu_doc_emi", length = 6)
    private String numeroDocumentoEmision;

    @Column(name = "de_doc_sig", length = 50)
    private String documentoSiguiente;

    @Column(name = "in_oficio", length = 1)
    private String indicadorOficio;

    @Column(name = "ti_gen_doc", length = 1)
    private String tipoGeneracionDocumento;

    @Column(name = "nu_ann_exp", length = 4)
    private String anioExpediente;

    @Column(name = "nu_sec_exp", length = 10)
    private String secuenciaExpediente;

    @Column(name = "nu_det_exp")
    private Short detalleExpediente;

    @Column(name = "nu_folios")
    private Double numeroFolios;

    @Column(name = "se_mesa_partes", length = 15)
    private String mesaPartes;

    @Column(name = "de_ori_emi", length = 400)
    private String origenEmision;

    @Column(name = "de_ori_des", length = 400)
    private String origenDestino;

    @Column(name = "in_busca_texto", length = 1)
    private String indicadorBusquedaTexto;

    @Column(name = "fec_enviodep")
    private LocalDateTime fechaEnvioDependencia;

    @Column(name = "fec_recepmp")
    private LocalDateTime fechaRecepcionMesaPartes;

    @Column(name = "doc_estado_msj", length = 1)
    private String estadoMensajeDocumento;

    @Column(name = "cod_dep_msj", length = 5)
    private String codigoDependenciaMensaje;

    @Column(name = "co_dep", length = 5)
    private String codigoDependenciaOrigen;


    // ------------------------------
    // CAMPOS FALTANTES AGREGADOS
    // ------------------------------

    @Column(name = "ccod_origing", length = 2)
    private String codigoOrigenGenerado;

    @Column(name = "cnum_dnimsg", length = 8)
    private String numeroDniMensaje;

    @Column(name = "nro_sobreaut", length = 50)
    private String numeroSobreAut;

    @Column(name = "an_sobreaut", length = 4)
    private String anioSobreAut;

    @Column(name = "cobs_documento", length = 500)
    private String observacionDocumento;

    @Column(name = "cdoc_destram", length = 5)
    private String documentoDestramite;

    @Column(name = "cdir_remite", length = 200)
    private String direccionRemitente;

    @Column(name = "cexp_correoe", length = 100)
    private String correoExpediente;

    @Column(name = "ctelefono", length = 30)
    private String telefono;

    @Column(name = "ccod_dpto", length = 2)
    private String codigoDepartamento;

    @Column(name = "ccod_prov", length = 2)
    private String codigoProvincia;

    @Column(name = "ccod_dist", length = 2)
    private String codigoDistrito;

    @Column(name = "remi_nu_dni_emi", length = 8)
    private String remiNumeroDniEmisor;

    @Column(name = "remi_co_otr_ori_emi", length = 10)
    private String remiOtroOrigenEmisor;

    @Column(name = "remi_cargo", length = 200)
    private String remiCargo;

    @Column(name = "cong_co_otr_ori", length = 10)
    private String congresoOtroOrigen;

    @Column(name = "ind_tipocong", length = 1)
    private String indicadorTipoCongreso;

    @Column(name = "ind_tipoconginv", length = 1)
    private String indicadorTipoCongresoInv;

    @Column(name = "remi_ti_emi", length = 2)
    private String remiTipoEmision;

    @Column(name = "aut_correoe", length = 1)
    private String autorizadoCorreo;

    @Column(name = "ind_reitconginv", length = 1)
    private String indicadorReiteracionCongInv;

    @Column(name = "cind_sensible", length = 1)
    private String indicadorSensible;

    @Column(name = "co_tema")
    private Integer codigoTema;

    @Column(name = "nu_ruc_des", length = 11)
    private String rucDestinatario;

    @Column(name = "ti_env_msj", length = 1)
    private String tipoEnvioMensaje;

    @Column(name = "uni_org_dest", length = 200)
    private String unidadOrganicaDestino;

    @Column(name = "nom_dest", length = 200)
    private String nombreDestinatario;

    @Column(name = "de_car_dest", length = 100)
    private String cargoDestinatario;

    @Column(name = "nu_anex")
    private Double numeroAnexos;

    @Column(name = "nu_fol")
    private Double numeroFolioAdicional;

    @Column(name = "url_doc_anx", length = 500)
    private String urlDocumentoAnexo;

    @Column(name = "fe_env_mes")
    private LocalDateTime fechaEnvioMesa;

    @Column(name = "cod_ver_ext", length = 10)
    private String codigoVersionExterna;

    @Column(name = "obs_doc", length = 300)
    private String observacionDoc;

    @Column(name = "nu_copia", length = 50)
    private String numeroCopia;

    @Column(name = "nu_anexo", length = 50)
    private String numeroAnexo;
}
