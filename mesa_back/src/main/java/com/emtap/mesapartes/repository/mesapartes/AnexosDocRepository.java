package com.emtap.mesapartes.repository.mesapartes;

import com.emtap.mesapartes.entity.mesapartes.AnexosDoc;
import com.emtap.mesapartes.entity.mesapartes.AnexosDocId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexosDocRepository extends JpaRepository<AnexosDoc, AnexosDocId> {
    
}