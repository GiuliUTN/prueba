package org.example.entities;

import jakarta.persistence.*;
import org.example.entities.Inasistencia;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiantes")
public class Estudiante implements Comparable<Estudiante> {
    @Id
    @Column(name = "estudiante_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "estudiante",fetch = FetchType.EAGER)
    List<Inasistencia> inasistencias;

    public long getCantidadJustificadas(){
        return inasistencias.stream().filter(i -> i.isJustificada()).count();
    }

    public Estudiante() {
        this.inasistencias = new ArrayList<>();
    }

    public Estudiante(String name) {
        this();
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void addInasistencia(Inasistencia inasistencia){
        this.inasistencias.add(inasistencia);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "name='" + name + '\'' +
                '}';
    }


    @Override
    public int compareTo(Estudiante o) {
        return this.name.compareTo(o.name);
    }
}