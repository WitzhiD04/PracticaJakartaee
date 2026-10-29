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


    public List<Emergencia> listar() {
        return em.createQuery("select e from Emergencia e ORDER BY e.id ASC", Emergencia.class).getResultList();
    }

    @Transactional
    public Emergencia guardar(Emergencia emergencia) {
        em.persist(emergencia);
        return emergencia;
    }

    public Optional<Emergencia> buscar(Long id) {
        return Optional.ofNullable(em.find(Emergencia.class, id));
    }

    @Transactional
    public Boolean eliminar(Long id) {
        Emergencia e = em.find(Emergencia.class, id);
        if (e == null) {
            return false;
        }
        em.remove(e);
        return true;
    }

    @Transactional
    public Boolean actualizar(Emergencia emergencia) {

        if (em.find(Emergencia.class, emergencia.getId()) == null) {
            return false;
        }
        em.merge(emergencia);
        return true;

    }

    public List<Emergencia> filtrar(String ciudad) {

        return em.createQuery("select e from Emergencia e WHERE e.ciudad = :ciudad ORDER BY e.id ASC",Emergencia.class ).setParameter("ciudad", ciudad)
                .getResultList();
    }
}
