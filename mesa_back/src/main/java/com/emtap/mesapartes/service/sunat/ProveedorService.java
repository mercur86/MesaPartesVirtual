package com.emtap.mesapartes.service.sunat;

import com.emtap.mesapartes.dto.sunat.EmpresaSunat;
import com.emtap.mesapartes.entity.sunat.LgProProveedor;
import com.emtap.mesapartes.repository.sunat.LgProProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final LgProProveedorRepository lgProProveedorRepository;

    @Transactional
    public void registrarSiNoExiste(EmpresaSunat empresa) {

        if (empresa == null || empresa.getNumero_documento() == null || empresa.getNumero_documento().isBlank()) {
            log.warn("No se puede registrar proveedor: RUC nulo o vacío");
            return;
        }

        boolean existe = lgProProveedorRepository.existsById(empresa.getNumero_documento());

        if (!existe) {
            LgProProveedor proveedor = new LgProProveedor();
            proveedor.setCproRuc(empresa.getNumero_documento());
            proveedor.setCproRazsoc(empresa.getRazon_social() != null ? empresa.getRazon_social() : "");
            proveedor.setCproSiglas(empresa.getNombre_comercial());
            proveedor.setCproDomicil(empresa.getDireccion());
            proveedor.setCproUbigeo(empresa.getUbigeo());

            // El ubigeo tiene 6 dígitos: DDPPII (dep 2, prov 2, dis 2)
            // Los campos cubi_coddep/codpro/coddis son varchar(2), NO usar los nombres de
            // texto
            String ubigeo = empresa.getUbigeo();
            if (ubigeo != null && ubigeo.length() == 6) {
                proveedor.setCubiCoddep(ubigeo.substring(0, 2));
                proveedor.setCubiCodpro(ubigeo.substring(2, 4));
                proveedor.setCubiCoddis(ubigeo.substring(4, 6));
            }

            proveedor.setDproFecins(LocalDateTime.now());

            lgProProveedorRepository.save(proveedor);
            log.info("Proveedor registrado: RUC={}, RazónSocial={}", empresa.getNumero_documento(),
                    empresa.getRazon_social());
        } else {
            log.info("Proveedor ya existe: RUC={}", empresa.getNumero_documento());
        }
    }
}
