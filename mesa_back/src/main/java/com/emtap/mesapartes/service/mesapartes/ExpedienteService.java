package com.emtap.mesapartes.service.mesapartes;

import com.emtap.mesapartes.dto.mesapartes.ExpedienteDocExtRecepBean;
import com.emtap.mesapartes.dto.mesapartes.RemitoRequestDTO;
import com.emtap.mesapartes.dto.reniec.Persona;
import com.emtap.mesapartes.dto.sunat.EmpresaSunat;
import com.emtap.mesapartes.service.sunat.ApiSunatService;
import com.emtap.mesapartes.service.sunat.ProveedorService;
import com.emtap.mesapartes.entity.mesapartes.Expediente;
import com.emtap.mesapartes.entity.mesapartes.ExpedienteId;
import com.emtap.mesapartes.entity.mesapartes.Remito;
import com.emtap.mesapartes.entity.mesapartes.AnexosDoc;
import com.emtap.mesapartes.entity.mesapartes.AnexosDocId;
import com.emtap.mesapartes.repository.mesapartes.ExpedienteRepository;
import com.emtap.mesapartes.repository.mesapartes.AnexosDocRepository;
import com.emtap.mesapartes.service.reniec.ApiReniecService;
import com.emtap.mesapartes.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final RemitoService remitoService;
    private final ArchivoDocService archivoDocService;
    private final PersonaService personaService;
    private final ApiReniecService apiReniecService;
    private final ApiSunatService apiSunatService;
    private final ProveedorService proveedorService;
    private final EmailService emailService;
    private final AnexosDocRepository anexosDocRepository;

    public ExpedienteService(ExpedienteRepository expedienteRepository,
            RemitoService remitoService, ArchivoDocService archivoDocService,
            PersonaService personaService,
            ApiReniecService apiReniecService,
            ApiSunatService apiSunatService,
            ProveedorService proveedorService,
            EmailService emailService,
            AnexosDocRepository anexosDocRepository) {
        this.expedienteRepository = expedienteRepository;
        this.remitoService = remitoService;
        this.archivoDocService = archivoDocService;
        this.personaService = personaService;
        this.apiReniecService = apiReniecService;
        this.apiSunatService = apiSunatService;
        this.proveedorService = proveedorService;
        this.emailService = emailService;
        this.anexosDocRepository = anexosDocRepository;
    }

    @Transactional
    public String procesarExpediente(RemitoRequestDTO mpv, MultipartFile file, Persona persona) {

        Expediente expedienteGuardado = insExpedienteBean(mpv);

        String nuAnn = null;
        String nuEmi = null;

        if (expedienteGuardado.getId() != null &&
                expedienteGuardado.getId().getSecuenciaExpediente() != null &&
                !expedienteGuardado.getId().getSecuenciaExpediente().isBlank()) {

            nuAnn = expedienteGuardado.getId().getAnioExpediente();

            ExpedienteDocExtRecepBean bean = mapToBean(expedienteGuardado);

            Remito remito = remitoService.insDocumentoExtBean(
                    mpv.getDocumento(),
                    bean,
                    mpv.getRemitente(), persona);
            nuAnn = expedienteGuardado.getId().getAnioExpediente();
            nuEmi = remito.getId().getNumeroEmision();
            // 3️⃣ Guardar archivo
            if (file != null && !file.isEmpty() && nuAnn != null && nuEmi != null) {

                archivoDocService.guardarArchivo(nuAnn, nuEmi, file);
            }
        }

        return nuEmi;
    }

    @Transactional
    public Expediente insExpedienteBean(RemitoRequestDTO mpv) {

        String anio = mpv.getExpediente().getNuAnnExp();

        // Generar clave aleatoria
        String clave = Utils.generateRandomLetter(4);

        Integer nextSec = expedienteRepository.getNextSecuencia(anio);
        Integer nextCorrExp = expedienteRepository.getNextCorrExp(anio);

        String snuSecExp = String.format("%010d", nextSec);
        String nuCorrExp = String.format("%07d", nextCorrExp);

        Expediente expediente = new Expediente();
        expediente.setId(new ExpedienteId(anio, snuSecExp));
        expediente.setFechaExpediente(LocalDateTime.now());
        expediente.setCodigoDependencia("00085");
        expediente.setCodigoGrupo("3");
        expediente.setNumeroCorrelativo(nuCorrExp);
        expediente.setNumeroExpediente(anio + "-" + nuCorrExp);
        expediente.setUsuarioCrea("MPVC");
        expediente.setFechaCrea(LocalDateTime.now());
        expediente.setCodigoTipoExpediente("20");
        expediente.setTipoRecepcionMesaPartes("0");
        expediente.setClave(clave);

        return expedienteRepository.save(expediente);
    }

    private ExpedienteDocExtRecepBean mapToBean(Expediente expediente) {

        ExpedienteDocExtRecepBean bean = new ExpedienteDocExtRecepBean();
        bean.setCoDepEmi(expediente.getCodigoDependencia());
        bean.setNuAnnExp(expediente.getId().getAnioExpediente());
        bean.setNuSecExp(expediente.getId().getSecuenciaExpediente());
        bean.setFeExp(expediente.getFechaExpediente());
        bean.setCoProceso(expediente.getCodigoProceso());
        bean.setDeDetalle(expediente.getDetalle());
        bean.setNuCorrExp(expediente.getNumeroCorrelativo());
        bean.setNuExpediente(expediente.getNumeroExpediente());
        bean.setNuFolios(expediente.getNumeroFolios());
        bean.setNuPlazo(expediente.getPlazo());
        bean.setUsCreaAudi(expediente.getUsuarioCrea());

        return bean;
    }

    @Transactional(rollbackFor = Exception.class)
    public String procesarExpedienteCompleto(RemitoRequestDTO mpv,
            MultipartFile file, List<MultipartFile> anexos) {

        String nuDni = mpv.getRemitente() != null ? mpv.getRemitente().getNuDni() : null;
        String nuRuc = mpv.getRemitente() != null ? mpv.getRemitente().getNuRuc() : null;

        Persona persona = null;
        EmpresaSunat empresa = null;

        // 1️⃣ Detectar si viene DNI o RUC y consultar la API correspondiente
        if (nuDni != null && !nuDni.isBlank()) {
            try {
                log.info("Remitente con DNI: {}, consultando RENIEC", nuDni);
                persona = apiReniecService.obtenerDatos(nuDni).block();
            } catch (Exception e) {
                log.warn("No se pudo obtener datos de RENIEC. Se continúa el flujo. DNI: {}", nuDni);
            }
            // Registrar persona solo si se obtuvo algo
            if (persona != null) {
                personaService.registrarSiNoExiste(persona);
            }

        } else if (nuRuc != null && !nuRuc.isBlank()) {
            try {
                log.info("Remitente con RUC: {}, consultando SUNAT", nuRuc);
                empresa = apiSunatService.obtenerDatos(nuRuc).block();
                log.info("Datos SUNAT obtenidos: {}", empresa);
            } catch (Exception e) {
                log.warn("No se pudo obtener datos de SUNAT. Se continúa el flujo. RUC: {}", nuRuc);
            }
            // Registrar proveedor solo si se obtuvo algo
            if (empresa != null) {
                proveedorService.registrarSiNoExiste(empresa);
            }

        } else {
            log.warn("Remitente sin DNI ni RUC, se omite consulta externa");
        }

        // 2️⃣ Insertar expediente
        Expediente expediente = insExpedienteBean(mpv);

        // 3️⃣ Insertar remito (se pasa persona; si vino por RUC, persona queda null)
        Remito remito = remitoService.insDocumentoExtBean(
                mpv.getDocumento(),
                mapToBean(expediente),
                mpv.getRemitente(), persona);
        String nuAnn = expediente.getId().getAnioExpediente();
        String nuEmi = remito.getId().getNumeroEmision();
        // Guardar archivo adjunto
        if (file != null && !file.isEmpty() && nuAnn != null && nuEmi != null) {
            archivoDocService.guardarArchivo(nuAnn, nuEmi, file);
        }

        // Guardar anexos
        if (anexos != null && !anexos.isEmpty() && nuAnn != null && nuEmi != null) {
            int contadorAnexo = 1;
            for (MultipartFile archivoAnexo : anexos) {
                if (archivoAnexo != null && !archivoAnexo.isEmpty()) {
                    try {
                        AnexosDocId anexoId = new AnexosDocId(nuAnn, nuEmi, contadorAnexo);
                        AnexosDoc anexoDoc = new AnexosDoc();
                        anexoDoc.setId(anexoId);
                        anexoDoc.setBlDoc(archivoAnexo.getBytes());
                        anexosDocRepository.save(anexoDoc);
                        log.info("Anexo {} guardado correctamente", contadorAnexo);
                        contadorAnexo++;
                    } catch (Exception e) {
                        log.error("Error al guardar anexo {}: {}", contadorAnexo, e.getMessage());
                    }
                }
            }
        }

        // Enviar correo de notificación a Mesa de Partes (en segundo plano)
        String nombreRemitente = resolverNombreRemitente(persona, empresa, mpv);
        String correoRemitente = mpv.getRemitente() != null ? mpv.getRemitente().getDeCorreo() : null;
        String asunto = mpv.getDocumento() != null ? mpv.getDocumento().getDeAsu() : "Sin asunto";
        emailService.enviarNotificacionMesaPartes(
                expediente.getNumeroExpediente(),
                nuEmi, nuAnn,
                nombreRemitente,
                correoRemitente,
                asunto, file);

        return expediente.getNumeroExpediente();
    }

    /**
     * Determina el nombre del remitente según el tipo de registro (DNI, RUC u
     * otro).
     */
    private String resolverNombreRemitente(Persona persona, EmpresaSunat empresa, RemitoRequestDTO mpv) {
        if (persona != null) {
            return (persona.getFirst_name() + " " + persona.getFirst_last_name() + " " + persona.getSecond_last_name())
                    .trim();
        }
        if (empresa != null) {
            return empresa.getRazon_social() != null ? empresa.getRazon_social() : empresa.getNombre_comercial();
        }
        if (mpv.getRemitente() != null && mpv.getRemitente().getDeNomOtrosRes() != null) {
            return mpv.getRemitente().getDeNomOtrosRes();
        }
        return "Ciudadano";
    }
}
