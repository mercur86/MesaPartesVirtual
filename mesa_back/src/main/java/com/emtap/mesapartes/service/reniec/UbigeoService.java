package com.emtap.mesapartes.service.reniec;

import com.emtap.mesapartes.entity.reniec.Ubigeo;
import com.emtap.mesapartes.repository.reniec.UbigeoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UbigeoService {

    private final UbigeoRepository ubigeoRepository;

    public UbigeoService(UbigeoRepository ubigeoRepository) {
        this.ubigeoRepository = ubigeoRepository;
    }

    public List<Ubigeo> obtenerListaUbigeo() {
        return ubigeoRepository.findAll();
    }
}
