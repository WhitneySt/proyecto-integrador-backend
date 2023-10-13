package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Transaccion")
public class TransaccionJPAEntity {
    @Id
    private Integer Id;
    @Column(length = 50, nullable = false)
    private Date fechaTransaccion;

    @Column(length = 50, nullable = false)
    private Double monto;

    @Column(length = 250, nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idTipoTransaccion", referencedColumnName = "id", nullable = false)
    private TipoTransaccionJPAEntity idTipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "idCuentaOrigen",referencedColumnName = "id", nullable = true)
    private CuentaJPAEntity idCuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "idCuentaDestino",referencedColumnName = "id", nullable = true)
    private CuentaJPAEntity idCuentaDestino;

    @ManyToOne
    @JoinColumn(name = "idBolsilloOrigen",referencedColumnName = "id", nullable = true)
    private BolsilloJPAEntity idBolsilloOrigen;

    @ManyToOne
    @JoinColumn(name = "idBolsilloDestino",referencedColumnName = "id", nullable = true)
    private BolsilloJPAEntity idBolsilloDestino;

    @ManyToOne
    @JoinColumn(name = "idTipoMovimiento", referencedColumnName = "id", nullable = false)
    private TipoMovimientoJPAEntity idTipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private UsuarioJPAEntity usuarioId;
}
