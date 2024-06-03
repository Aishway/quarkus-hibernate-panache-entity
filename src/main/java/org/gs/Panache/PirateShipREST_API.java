package org.gs.Panache;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/ship")
public class PirateShipREST_API {

    @Inject
    EntityManager em;

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test (){
        return  Response.ok("Say Hello").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShips() {
        List<Ship> ships = Ship.listAll();
        return Response.ok(ships).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByID(@PathParam("id") Long id) {
        return Ship.findByIdOptional(id)
                .map(ship -> Response.ok(ship).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/direction/{direction}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByDirection(@PathParam("direction") String direction){
       Query q = em.createNativeQuery("SELECT * FROM Ship WHERE direction = ?1");
       q.setParameter(1, direction);
       List <Object[]> ships = q.getResultList();
        return Response.ok(ships).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create (Ship ship) {
        Ship.persist(ship);
        if(ship.isPersistent()){
            return Response.created(URI.create("/ship" + ship.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteByID(@PathParam("id")int id){
        boolean deleted = Ship.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
