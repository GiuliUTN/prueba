package org.example.services;

import jakarta.persistence.EntityManager;
import org.example.entities.Estudiante;
import org.example.entities.Inasistencia;
import org.example.entities.Tipo;
import org.example.repositories.InasistenciaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InasistenciaService {
    private static final String INASISTENCIA_FILE_PATH = "src/main/resources/META-INF/inasistencias.csv";

    private EstudianteService estudiantes;
    private TipoService tipos;
    private InasistenciaRepository repository;
    private List<Inasistencia> inasistencias;

    public InasistenciaService(EntityManager entityManager){
        this.repository = new InasistenciaRepository(entityManager);
        this.tipos = new TipoService();
        this.estudiantes = new EstudianteService();
        this.inasistencias = new ArrayList<>();
    }

    public Inasistencia lineToInasistencia (String line){
        String[] parte = line.split(",");
        String estudianteName = parte[0];
        Estudiante estudiante = this.estudiantes.getOrCreate(estudianteName);
        String tipoName = parte[1];
        Tipo tipo = this.tipos.getOrCreate(tipoName);
        int justificada = Integer.parseInt(parte[2]);
        double cantidad = Double.parseDouble(parte[3]);

        return new Inasistencia(justificada, cantidad, tipo, estudiante);
    }



    public void mostrarTotalDeCantidad(){
        double cantidadTotal = inasistencias.stream().mapToDouble(inasistencia -> {return inasistencia.getCantidad();}).sum();
        System.out.println("La cantidad total de dias es: " + cantidadTotal);
    }

    public void generarArchivo(){
        Map<String, AtomicInteger> counters = new HashMap<>();
        inasistencias.forEach(inasistencia -> {
            String tipoName = inasistencia.getTipo().getName();
            if (counters.containsKey(tipoName)){
                counters.get(tipoName).getAndIncrement();
            } else {
                counters.put(tipoName, new AtomicInteger(1));
            }
        });
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(INASISTENCIAXTIPO_FILE_PATH))){
            writer.append("NombreTipo,Cantidad\n");
            for(Map.Entry<String, AtomicInteger> entrada: counters.entrySet()){
                writer.append(entrada.getKey()).append(",")
                        .append(entrada.getValue().toString())
                        .append("\n");
            }
            writer.flush();
            System.out.println("Archivo generado correctamente en: " + INASISTENCIAXTIPO_FILE_PATH);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void mostrarEstudiantes(){
        List<String> lista = estudiantes.getEstudianteMenos5Just();
        System.out.println("los estudiantes con menos de 5 justificaciones: ");
        lista.forEach(System.out::println);
    }

    public void guardarDB(){
        repository.saveAll(inasistencias);
    }

    public void cargarArchivo(){
        try(BufferedReader reader = new BufferedReader(new FileReader(INASISTENCIA_FILE_PATH))){
            String line = reader.readLine();
            while ((line = reader.readLine()) != null){
                Inasistencia inasistencia = lineToInasistencia(line);
                inasistencias.add(inasistencia);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
