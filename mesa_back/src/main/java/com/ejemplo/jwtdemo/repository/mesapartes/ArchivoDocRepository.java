package com.ejemplo.jwtdemo.repository.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.ArchivoDocId;
import com.ejemplo.jwtdemo.entity.mesapartes.ArchivosDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoDocRepository extends JpaRepository<ArchivosDoc, ArchivoDocId> {

}
