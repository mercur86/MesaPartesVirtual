package com.emtap.mesapartes.repository.mesapartes;

import com.emtap.mesapartes.entity.mesapartes.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDocumentoRepository  extends JpaRepository<TipoDocumento, String> {
    List<TipoDocumento> findByCdocIndbajNot(String indicadorBaja);
}
