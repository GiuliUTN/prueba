package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "inasistencias")
public class Inasistencia {
    @Id
    @Column(name = "inasistencia_id")
    private Integer id;
    private int justificada;
    private double cantidad;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    public Inasistencia(){
    }

    public Inasistencia(int justificada, double cantidad, Tipo tipo, Estudiante estudiante) {
        this();
        this.justificada = justificada;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.estudiante = estudiante;

        this.estudiante.addInasistencia(this);
    }

    public Integer getId() {
        return id;
    }

    public boolean isJustificada() {
        return this.justificada == 1;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }
}
