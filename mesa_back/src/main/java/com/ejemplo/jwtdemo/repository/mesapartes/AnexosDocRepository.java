package com.ejemplo.jwtdemo.repository.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.AnexosDoc;
import com.ejemplo.jwtdemo.entity.mesapartes.AnexosDocId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexosDocRepository extends JpaRepository<AnexosDoc, AnexosDocId> {
    
}