package org.example.services;

import org.example.entities.Tipo;

import java.util.HashMap;
import java.util.Map;

public class TipoService {
    private Map<String, Tipo> tipos = new HashMap<>();

    public TipoService(){}

    public Tipo getOrCreate(String name){
        if (this.tipos.containsKey(name)){
            return (Tipo) this.tipos.get(name);
        } else {
            Tipo tipo = new Tipo(name);
            this.tipos.put(name, tipo);
            return tipo;
        }
    }
}
