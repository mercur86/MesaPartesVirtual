package com.emtap.mesapartes.service.mesapartes;

import com.emtap.mesapartes.dto.reniec.Persona;
import com.emtap.mesapartes.entity.mesapartes.IdTanirs;
import com.emtap.mesapartes.repository.mesapartes.IdTanirsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final IdTanirsRepository idTanirsRepository;

    @Transactional
    public void registrarSiNoExiste(Persona persona) {

        if (persona == null || persona.getDocument_number() == null) {
            return;
        }

        boolean existe = idTanirsRepository.existsByNulem(persona.getDocument_number());

        if (!existe) {

            IdTanirs entidad = new IdTanirs();
            entidad.setNulem(persona.getDocument_number());
            entidad.setDeapp(persona.getFirst_last_name());
            entidad.setDeapm(persona.getSecond_last_name());
            entidad.setDenom(persona.getFirst_name());

            idTanirsRepository.save(entidad);
        }
    }
}
