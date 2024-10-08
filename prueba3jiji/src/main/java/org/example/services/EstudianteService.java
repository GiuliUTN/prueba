package org.example.services;

import org.example.entities.Estudiante;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstudianteService {
    private Map<String, Estudiante> estudiantes = new HashMap<>();

    public EstudianteService() {
    }

    public Estudiante getOrCreate(String name){
        if (this.estudiantes.containsKey(name)){
            return (Estudiante) this.estudiantes.get(name);
        } else {
            Estudiante estudiante = new Estudiante(name);
            this.estudiantes.put(name, estudiante);
            return estudiante;
        }
    }

    public List<String> getEstudianteMenos5Just(){
        List<String> listaEstudiantes = estudiantes.values()
                .stream().filter(estudiante -> estudiante.getCantidadJustificadas() < 5)
                .sorted().map(Estudiante::toString).toList();
        return listaEstudiantes;
    }
}
