package com.emtap.mesapartes.entity.mesapartes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "idosgd", name = "idtanirs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdTanirs {

    @Id
    @Column(name = "nulem", length = 8, nullable = false)
    private String nulem;

    @Column(name = "ubdep", length = 2)
    private String ubdep;

    @Column(name = "ubprv", length = 2)
    private String ubprv;

    @Column(name = "ubdis", length = 2)
    private String ubdis;

    @Column(name = "deapp", length = 40)
    private String deapp;

    @Column(name = "deapm", length = 40)
    private String deapm;

    @Column(name = "denom", length = 35)
    private String denom;

    @Column(name = "insex", length = 1)
    private String insex;

    @Column(name = "fenac", length = 8)
    private String fenac;

    @Column(name = "dedomicil", length = 100)
    private String dedomicil;

    @Column(name = "deemail", length = 100)
    private String deemail;

    @Column(name = "detelefo", length = 30)
    private String detelefo;
}
