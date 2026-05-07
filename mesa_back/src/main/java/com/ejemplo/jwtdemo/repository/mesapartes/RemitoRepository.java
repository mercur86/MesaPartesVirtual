package com.ejemplo.jwtdemo.repository.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.Remito;
import com.ejemplo.jwtdemo.entity.mesapartes.RemitoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RemitoRepository extends JpaRepository<Remito, RemitoId> {
    @Query(value = "SELECT LPAD(CAST(nextval('IDOSGD.SEC_REMITOS_NU_EMI') AS text), 10, '0')", nativeQuery = true)
    String getNextNuEmi();

    @Query("SELECT COALESCE(MAX(CAST(e.correlativoDocumento AS int)), 0) + 1 " +
            "FROM Remito e WHERE e.anioExpediente = :anio")
    Integer getNuCorEmi(String anio);

    @Query("SELECT r FROM Remito r WHERE r.anioExpediente = :nuAnn AND r.id.numeroEmision = :nuEmi")
    Optional<Remito> findByAnioAndNumeroEmision(@Param("nuAnn") String nuAnn, @Param("nuEmi") String nuEmi);
}
