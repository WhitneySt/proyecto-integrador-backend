package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "TipoMovimiento")
public class TipoMovimientoJPAEntity {
    @Id
    private Integer Id;

    @Column(length = 45, nullable = false)
    private String codigoOrigen;

    @Column(length = 45, nullable = false)
    private String codigoDestino;

    @Column(length = 250, nullable = false)
    private String descripcion;
}
