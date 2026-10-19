package donaciones.infraestructura;

import donaciones.dominio.Emergencia;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped   // UN solo objeto para toda la aplicación
public class EmergenciaRepositorio {

    private final Map<Long, Emergencia> datos = new HashMap<Long, Emergencia>();
    private Long secuencia = 0L; // Para generar los id


    // El servidor lo llama una vez, justo después de crear el objeto
    @PostConstruct
    void cargarDatosEjemplo() {
        guardar(new Emergencia("Inundación en Mocoa", "Mocoa", 50000000L));
        guardar(new Emergencia("Incendio forestal", "Santa Marta", 20000000L));
    }

    public synchronized List<Emergencia> listar() {
        return new ArrayList<Emergencia>(datos.values());
    }

    public synchronized Emergencia guardar(Emergencia emergencia) {
        if (emergencia.getId() == null) {
            emergencia.setId(secuencia);
            secuencia++;
        }
        datos.put(emergencia.getId(), emergencia);
        return emergencia;
    }

    public synchronized Optional<Emergencia> buscar(Long id) {
        return Optional.ofNullable(datos.get(id));
    }

    public synchronized Boolean eliminar(Long id) {
        return datos.remove(id) != null;
    }

    public synchronized Boolean actualizar(Emergencia emergencia) {
        if (datos.get(emergencia.getId()) == null) {
            return false;
        } else {
            datos.put(emergencia.getId(), emergencia) ;
            return true;
        }
    }

    public synchronized List<Emergencia> filtrar(String ciudad) {
        List<Emergencia> resultado = new ArrayList<Emergencia>();
        for (Emergencia emergencia : datos.values()) {
            if (emergencia.getCiudad().equals(ciudad)) {
                resultado.add(emergencia);
            }
        }
        return resultado;
    }
}
