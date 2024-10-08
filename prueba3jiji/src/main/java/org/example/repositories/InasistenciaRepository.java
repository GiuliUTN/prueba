package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.entities.Inasistencia;

import java.util.Collection;

public class InasistenciaRepository {
    private EntityManager entityManager;

    public InasistenciaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void begin(){ this.entityManager.getTransaction().begin();}
    public void commit(){ this.entityManager.getTransaction().commit();}
    public void save(Inasistencia toBeSaved){
        this.begin();
        this.entityManager.persist(toBeSaved);
        this.commit();
    }
    public void saveAll(Collection<Inasistencia> toBeSaved){
        toBeSaved.forEach(this::save);
    }
}
