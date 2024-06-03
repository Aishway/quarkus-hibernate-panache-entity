package org.gs.Panache;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pirate")
public class PirateREST_API {

    @Inject
    PirateRepository pirateRepository;

    @GET
    public Response countPirates(){
        return Response.ok(pirateRepository.listAll()).build();
    }

    @POST
    @Path("/addPirate")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create (Pirate pirate) {
         pirateRepository.persist(pirate);
        if(pirateRepository.isPersistent(pirate)){
            pirateRepository.flush();
            return Response.ok(pirate.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/status/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePirateStatus(@PathParam("id") Long id){
        Pirate pirate = pirateRepository.findById(id);
        pirate.setStatus("dead");
        pirateRepository.persistAndFlush(pirate);
        return Response.ok(pirateRepository.findDead()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)

    public Response deleteByID(@PathParam("id") Long id){
        Pirate pirate = pirateRepository.findById(id);
        pirateRepository.delete(pirate);
        pirateRepository.flush();
        return Response.ok("successfully deleted").build();
    }
}
