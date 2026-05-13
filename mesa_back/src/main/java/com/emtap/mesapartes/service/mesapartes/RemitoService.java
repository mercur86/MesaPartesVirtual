package com.emtap.mesapartes.service.mesapartes;

import com.emtap.mesapartes.dto.mesapartes.DocumentoExtRecepBean;
import com.emtap.mesapartes.dto.mesapartes.ExpedienteDocExtRecepBean;
import com.emtap.mesapartes.dto.mesapartes.RemitenteDocExtRecepBean;
import com.emtap.mesapartes.dto.reniec.Persona;
import com.emtap.mesapartes.entity.mesapartes.Remito;
import com.emtap.mesapartes.entity.mesapartes.RemitoId;
import com.emtap.mesapartes.repository.mesapartes.RemitoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RemitoService {

    private final RemitoRepository remitoRepository;

    @Value("${remito.codigo-local-emision}")
    private String codigoLocalEmision;

    @Value("${remito.codigo-dependencia-emision}")
    private String codigoDependenciaEmision;

    @Value("${remito.codigo-dependencia-origen}")
    private String codigoDependenciaOrigen;

    @Value("${remito.codigo-empleado-emisor}")
    private String codigoEmpleadoEmisor;

    @Value("${remito.usuario-crea}")
    private String usuarioCrea;

    @Value("${remito.codigo-empleado-responsable}")
    private String codigoEmpleadoResponsable;

    public RemitoService(RemitoRepository remitoRepository) {
        this.remitoRepository = remitoRepository;
    }

    @Transactional
    public Remito insDocumentoExtBean(DocumentoExtRecepBean documentoBean,
            ExpedienteDocExtRecepBean expedienteBean,
            RemitenteDocExtRecepBean remitenteBean,
            Persona persona

    ) {

        // 1. Generar nuEmi con secuencia
        String nuEmi = remitoRepository.getNextNuEmi();
        documentoBean.setNuEmi(nuEmi);

        // 2. Construcción del Remito
        Remito remito = new Remito();
        remito.setId(new RemitoId(expedienteBean.getNuAnnExp(), nuEmi));

        remito.setNumeroCorrelativoEmision(
                expedienteBean.getNuCorrExp() != null
                        ? Long.valueOf(expedienteBean.getNuCorrExp())
                        : null);

        Integer nuCorDoc = remitoRepository.getNuCorEmi(expedienteBean.getNuAnnExp());
        remito.setCodigoLocalEmision(codigoLocalEmision);
        remito.setCodigoDependenciaEmision(codigoDependenciaEmision);
        remito.setCodigoDependenciaOrigen(codigoDependenciaOrigen);
        remito.setCodigoDepartamento(remitenteBean.getIdDepartamento());
        remito.setCodigoProvincia(remitenteBean.getIdProvincia());
        remito.setCodigoDistrito(remitenteBean.getIdDistrito());
        remito.setTipoEmision("03");
        remito.setDniEmisor(remitenteBean.getNuDni());
        remito.setRemiNumeroDniEmisor(remitenteBean.getNuDni());
        remito.setCodigoEmpleadoEmisor(codigoEmpleadoEmisor);
        remito.setRucEmisor(remitenteBean.getNuRuc());
        remito.setFechaEmision(expedienteBean.getFeExp());
        remito.setCodigoGrupo("3");//
        remito.setAsunto(documentoBean.getDeAsu());
        remito.setEstadoDocumentoEmision("5"); // en proyecto
        remito.setDiasAtencion((short) 0);
        remito.setEliminado("0");
        remito.setUsuarioCrea(usuarioCrea);
        remito.setFechaCrea(LocalDateTime.now());
        remito.setUsuarioModifica(usuarioCrea);
        remito.setFechaModifica(LocalDateTime.now());
        remito.setTipoDocumentoAdm(documentoBean.getCoTipDocAdm());
        remito.setActualizado("0");
        remito.setCodigoEmpleadoResponsable(codigoEmpleadoResponsable);
        remito.setCantidadDestinatarios((short) 1);
        remito.setCorrelativoDocumento(nuCorDoc);
        remito.setDocumentoSiguiente(documentoBean.getNuDoc());
        remito.setAnioExpediente(expedienteBean.getNuAnnExp());
        remito.setSecuenciaExpediente(expedienteBean.getNuSecExp());
        remito.setTelefono(remitenteBean.getTelefono());
        remito.setCodigoDepartamento(remitenteBean.getIdDepartamento());
        remito.setCodigoProvincia(remitenteBean.getIdProvincia());
        remito.setCodigoDistrito(remitenteBean.getIdDistrito());
        remito.setRemiTipoEmision("03");
        remito.setCorreoExpediente(remitenteBean.getDeCorreo());
        remito.setDetalleExpediente((short) 1);
        remito.setNumeroFolios((double) 0);
        remito.setCodigoOrigenGenerado("08");
        remito.setDireccionRemitente(remitenteBean.getDeDireccion());
        remito.setIndicadorOficio("0");
        if (persona != null) {
            remito.setOrigenEmision(
                    persona.getFirst_last_name() + ' ' + persona.getSecond_last_name() + ' ' + persona.getFirst_name());
        }
        return remitoRepository.save(remito);

    }

}
