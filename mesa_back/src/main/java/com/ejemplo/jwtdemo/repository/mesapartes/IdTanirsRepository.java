package com.ejemplo.jwtdemo.repository.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.IdTanirs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdTanirsRepository extends JpaRepository<IdTanirs, String> {

    boolean existsByNulem(String nulem);

}
