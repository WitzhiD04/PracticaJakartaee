package donaciones.acceso;


import donaciones.dominio.Donaciones;
import donaciones.dominio.Emergencia;
import donaciones.infraestructura.DonacionesRepositorio;
import donaciones.infraestructura.EmergenciaRepositorio;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Path("emergencias/{emergenciaId}/donaciones")
@Produces(MediaType.APPLICATION_JSON)       // responde JSON
@Consumes(MediaType.APPLICATION_JSON)       // recibe JSON
@RequestScoped
public class DonacionesResource {

    @Inject
    DonacionesRepositorio donacionesRepositorio;

    @Inject
    private EmergenciaRepositorio emergenciaRepositorio;

    // GET /api/emergencias/1/donaciones
    @GET
    public List<Donaciones> listarPorEmergencia(@PathParam("emergenciaId") Long emergenciaId) {
        buscarEmergencia(emergenciaId); // si no existe, 404
        return donacionesRepositorio.listarPorEmergencia(emergenciaId);
    }

    @POST
    public Response donar(@PathParam("emergenciaId") Long emergenciaId, Donaciones donacion) {
        if (donacion.getDonante() == null || donacion.getDonante().isBlank()) {
            throw new BadRequestException("El nombre del donante es obligatorio");
        }

        if (donacion.getMonto() == null || donacion.getMonto() <= 0) {
            throw new BadRequestException("El monto debe ser mayor que cero");
        }

        Emergencia emergencia = buscarEmergencia(emergenciaId);

        donacion.setId(null);
        donacion.setEmergencia(emergencia);
        donacion.setFecha(LocalDateTime.now());
        Donaciones creada = donacionesRepositorio.guardar(donacion);
        return Response.status(Response.Status.CREATED).entity(creada).build();
    }

    @GET()
    @Path("total")
    public Long totalPlata(@PathParam("emergenciaId") Long emergenciaId) {
        return donacionesRepositorio.totalPlata(emergenciaId);
    }

    private Emergencia buscarEmergencia(@PathParam("emergenciaId") Long emergenciaId) {

        Optional<Emergencia> emergencia = emergenciaRepositorio.buscar(emergenciaId);

        if (emergencia.isEmpty()) {
            throw new NotFoundException("No existe la emergencia " + emergenciaId);
        }
        return emergencia.get();
    }

}
