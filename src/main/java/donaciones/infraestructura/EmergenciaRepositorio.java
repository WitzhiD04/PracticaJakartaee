package donaciones.infraestructura;

import donaciones.dominio.Emergencia;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.*;

@ApplicationScoped   // UN solo objeto para toda la aplicación
public class EmergenciaRepositorio {

    // El servidor te entrega el EntityManager conectado a la base de datos
    @PersistenceContext(unitName = "donacionesPU")
    EntityManager em;


    public synchronized List<Emergencia> listar() {
        return em.createQuery("select e from Emergencia e ORDER BY e.id ASC", Emergencia.class).getResultList();
    }

    @Transactional
    public synchronized Emergencia guardar(Emergencia emergencia) {
        em.persist(emergencia);
        return emergencia;
    }

    public synchronized Optional<Emergencia> buscar(Long id) {
        return Optional.ofNullable(em.find(Emergencia.class, id));
    }

    @Transactional
    public synchronized Boolean eliminar(Long id) {
        if (em.contains(em.find(Emergencia.class, id))) {
            em.remove(em.find(Emergencia.class, id));
            return true;
        } else {
            return false;
        }
    }

    public synchronized Boolean actualizar(Emergencia emergencia) {

        if (em.contains(em.find(Emergencia.class, emergencia.getId()))) {
            em.merge(emergencia);
            return true;
        } else {
            return false;
        }

    }

    public synchronized List<Emergencia> filtrar(String ciudad) {

        return em.createQuery("select e from Emergencia e WHERE e.ciudad = :ciudad ORDER BY e.id ASC",Emergencia.class ).getResultList();
    }
}
