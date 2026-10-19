package donaciones.acceso;


import donaciones.dominio.Emergencia;
import donaciones.infraestructura.EmergenciaRepositorio;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("emergencias")                        // /api/emergencias
@Produces(MediaType.APPLICATION_JSON)       // responde JSON
@Consumes(MediaType.APPLICATION_JSON)       // recibe JSON
@RequestScoped
public class EmergenciaResource {

    @Inject
    EmergenciaRepositorio repositorio;

    @GET
    public List<Emergencia> listar() {
        return repositorio.listar();
    }

    @GET
    @Path("{id}")
    public Emergencia buscar(@PathParam("id") Long id) {
        Optional<Emergencia> emergencia = repositorio.buscar(id);
        if( emergencia.isPresent() ) {
            return emergencia.get();
        } else {
            throw new NotFoundException("No existe la emergencia " + id);
        }
    }

    public void validar(Emergencia emergencia) {
        if (emergencia.getNombre() == null || emergencia.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio");
        } else if (emergencia.getCiudad() == null || emergencia.getCiudad().isEmpty()) {
            throw new BadRequestException("La ciudad es obligatoria");
        } else if (emergencia.getMeta() == null || emergencia.getMeta() < 0) {
            throw new BadRequestException("La meta es obligatoria o no puede ser negativa");
        }
    }

    @POST
    public Response insertar(Emergencia emergencia) {
        emergencia.setId(null);
        validar(emergencia);
        Emergencia creada = repositorio.guardar(emergencia);
        return Response.status(Response.Status.CREATED).entity(creada).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = repositorio.eliminar(id);
        if (!eliminado) {
            throw new NotFoundException("No existe la emergencia " + id);
        }
        return Response.status(Response.Status.OK).entity(eliminado).build();
    }

    @PUT
    @Path("{id}")
    public Response modificar(@PathParam("id") Long id, Emergencia emergencia) {
        emergencia.setId(id);
        validar(emergencia);
        boolean modificado = repositorio.actualizar(emergencia);
        if (!modificado) {
            throw new NotFoundException("No existe la emergencia " + id);
        }
        return Response.status(Response.Status.OK).entity(emergencia).build();
    }

    @GET
    @Path("filtro")
    public List<Emergencia> filtrar(@QueryParam("ciudad") String ciudad) {
        if(ciudad == null) {
            return listar();
        }
        return repositorio.filtrar(ciudad);
    }


}
