package com.emtap.mesapartes.repository.mesapartes;

import com.emtap.mesapartes.entity.mesapartes.IdTanirs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdTanirsRepository extends JpaRepository<IdTanirs, String> {

    boolean existsByNulem(String nulem);

}
