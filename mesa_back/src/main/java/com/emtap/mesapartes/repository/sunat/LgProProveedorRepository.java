package com.emtap.mesapartes.repository.sunat;

import com.emtap.mesapartes.entity.sunat.LgProProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LgProProveedorRepository extends JpaRepository<LgProProveedor, String> {
}
