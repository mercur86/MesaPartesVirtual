package com.ejemplo.jwtdemo.entity.reniec;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_ubigeo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ubigeo {
    @Id
    private String id;

    @Column( nullable = false)
    private String nomdep;
    @Column( nullable = false)
    private String nomprov;
    @Column( nullable = false)
    private String nomdis;
    @Transient
    private String nombreCompleto;

    public String getNombreCompleto() {
        return String.join(" / ", nomdep, nomprov, nomdis);
    }
}
