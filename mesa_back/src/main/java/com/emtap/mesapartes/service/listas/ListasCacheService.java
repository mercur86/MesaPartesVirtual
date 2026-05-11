package com.emtap.mesapartes.service.listas;
import com.emtap.mesapartes.entity.reniec.Ubigeo;
import com.emtap.mesapartes.service.reniec.UbigeoService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ListasCacheService {
    private final UbigeoService ubigeoService;
    @Getter private List<Ubigeo> listaUbigeos;
    public ListasCacheService(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;

    }
    @PostConstruct
    public void init() {
        refrescar();
    }

    public synchronized void refrescar() {
        log.info("♻️ Cargando catálogos en memoria...");
        listaUbigeos=ubigeoService.obtenerListaUbigeo();
        log.info("✅ Catálogos cargados correctamente.");
    }
}
