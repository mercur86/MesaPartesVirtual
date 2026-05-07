package com.ejemplo.jwtdemo.repository.mesapartes;

import com.ejemplo.jwtdemo.entity.mesapartes.Expediente;
import com.ejemplo.jwtdemo.entity.mesapartes.ExpedienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteRepository extends JpaRepository<Expediente, ExpedienteId> {

    // Obtener el máximo nu_sec_exp para un año específico
    @Query("SELECT COALESCE(MAX(CAST(e.id.secuenciaExpediente AS int)), 0) + 1 " +
            "FROM Expediente e WHERE e.id.anioExpediente = :anio")
    Integer getNextSecuencia(String anio);

    @Query("SELECT COALESCE(MAX(CAST(e.numeroCorrelativo AS int)), 0) + 1 " +
            "FROM Expediente e WHERE e.id.anioExpediente = :anio")
    Integer getNextCorrExp (String anio);
}
