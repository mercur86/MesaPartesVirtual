package com.ejemplo.jwtdemo.repository.sunat;

import com.ejemplo.jwtdemo.entity.sunat.LgProProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LgProProveedorRepository extends JpaRepository<LgProProveedor, String> {
}
