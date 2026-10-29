package donaciones.infraestructura;


import donaciones.dominio.Donaciones;
import donaciones.dominio.Emergencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped   // UN solo objeto para toda la aplicación
public class DonacionesRepositorio {

    // El servidor te entrega el EntityManager conectado a la base de datos
    @PersistenceContext(unitName = "donacionesPU")
    EntityManager em;

    @Transactional
    public Donaciones guardar(Donaciones donacion) {
        em.persist(donacion);
        return donacion;
    }

    // JPQL: "d.emergencia.id" navega por la relación, sin escribir el JOIN a mano
    public List<Donaciones> listarPorEmergencia(Long emergenciaId) {
        return em.createQuery("select d from Donaciones d WHERE d.emergencia.id = :emergenciaId ORDER BY d.fecha ASC", Donaciones.class).setParameter("emergenciaId", emergenciaId).getResultList();
    }

    public Long totalPlata(Long emergenciaId) {

        Long total = em.createQuery("select SUM(d.monto) from Donaciones d WHERE d.emergencia.id = :emergenciaId", Long.class).setParameter("emergenciaId", emergenciaId).getSingleResult();

        if (total == null) {
            return 0L;
        }
        return total;

    }
}
