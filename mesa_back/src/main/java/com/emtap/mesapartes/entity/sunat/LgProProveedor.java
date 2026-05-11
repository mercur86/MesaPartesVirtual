package com.emtap.mesapartes.entity.sunat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(schema = "idosgd", name = "lg_pro_proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LgProProveedor {

    @Id
    @Column(name = "cpro_ruc", length = 11, nullable = false)
    private String cproRuc;

    @Column(name = "cpro_razsoc", length = 100, nullable = false)
    private String cproRazsoc;

    @Column(name = "cpro_siglas", length = 100)
    private String cproSiglas;

    @Column(name = "dpro_fecins", nullable = false)
    private LocalDateTime dproFecins;

    @Column(name = "cpro_domicil", length = 100)
    private String cproDomicil;

    @Column(name = "cpro_ubigeo", length = 12)
    private String cproUbigeo;

    @Column(name = "cpro_telefo", length = 30)
    private String cproTelefo;

    @Column(name = "cpro_email", length = 100)
    private String cproEmail;

    @Column(name = "cubi_coddep", length = 2)
    private String cubiCoddep;

    @Column(name = "cubi_codpro", length = 2)
    private String cubiCodpro;

    @Column(name = "cubi_coddis", length = 2)
    private String cubiCoddis;
}
