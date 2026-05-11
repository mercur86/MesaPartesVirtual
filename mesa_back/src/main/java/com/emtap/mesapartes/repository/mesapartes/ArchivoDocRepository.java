package com.emtap.mesapartes.repository.mesapartes;

import com.emtap.mesapartes.entity.mesapartes.ArchivoDocId;
import com.emtap.mesapartes.entity.mesapartes.ArchivosDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoDocRepository extends JpaRepository<ArchivosDoc, ArchivoDocId> {

}
