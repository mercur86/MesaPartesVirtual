package com.emtap.mesapartes.dto.mesapartes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoExtRecepBean {
    private String nuExpediente;
    private String nuEmi;
    
    @NotBlank(message = "El número de documento es obligatorio")
    private String nuDoc;
    
    private String tiEmi;
    private String deTiEmi;
    private String deTipDocAdm;
    private String deOriEmiMp;
    private String deEsDocEmiMp;
    private String deEmpDes;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String deAsu;
    private String coEsDocEmiMp;
    private String coDepEmi;
    private String coLocEmi;
    private String coEmpEmi;
    private String deEmpEmi;
    private String nuCorrExp;
    private String feExp;
    private String feExpCorta;
    private String coTipDocAdm;
    private Double nuFolios;
    private Integer nuCorDoc;
    private String nuCorEmi;
    private String coEmpRes;
    private String deDocSig;
    private String deDocSigG;
    private String deEmpRes;
    private String nuAnnExp;
    private String nuSecExp;
    private String nuDni;
    private String deNuDni;
    private String nuRuc;
    private String deNuRuc;
    private String coOtros;
    private String deNomOtros;
    private String deDocOtros;
    private String nuDocOtros;
    private String feVence;
    private String coProceso;
    private String feEmiCorta;
    private String existeDoc;
    private String inNumeroMp;
    private String coUseMod;
    private Short nuDiaAte;
    private String deLocEmi;
    private String deDependencia;
    private String idDepartamento;
    private String idProvincia;
    private String idDistrito;
    private String deDireccion;
    private String coTipoExp;
    private String coOriDoc;
    private String coTraDest;
    private String coComision;
    private String coTipoCongresista;
    private String nroDniTramitante;
    private String coTipoInv;
    private String nuDniRes;
    private String deNuDniRes;
    private String deCorreo;
    private String telefono;
    private String nSobre;
    private String anioSobre;
    private String sensible;
    private String notificado;
    private String deCargo;
    private String deObservacion;
    private String reiterativo;
    private String emiResp;
    private String coOtrosRes;
    private String deNomOtrosRes;
    private String tiRemitente;
    private String deRemitente;
    private String deEmpPro;
    private String coProcesoExp;
    private String deEsDocEmi;
    private String feExpVenceCorta;
    private String deProcesoExp;
    private String deEmpRec;

    private String nuAnexo;
    private String nuCopia;
    private String nuDiasHabiles;
}
