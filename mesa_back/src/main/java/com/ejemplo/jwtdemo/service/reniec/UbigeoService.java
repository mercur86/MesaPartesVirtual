package com.ejemplo.jwtdemo.service.reniec;

import com.ejemplo.jwtdemo.entity.reniec.Ubigeo;
import com.ejemplo.jwtdemo.repository.reniec.UbigeoRepository;
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
