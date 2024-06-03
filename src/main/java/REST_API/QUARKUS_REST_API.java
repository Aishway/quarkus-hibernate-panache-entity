package REST_API;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("test")
public class QUARKUS_REST_API {
    private List<Ship_R> shipList = new ArrayList<>();
    private List<Pirate_R> pirateList = new ArrayList<>();

    @GET
    public Response test(){
    return Response.ok("Always be yourself, unless you can be a pirate. Then always be a pirate.").build();
    }

    @POST
    @Path("addShip")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShip(List<Ship_R> list){
        if (list.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.shipList.addAll(list);
        return Response.ok(this.shipList ).build();
    }

    @POST
    @Path("addPirate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPirate(List<Pirate_R> list){
        if (list.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.pirateList.addAll(list);
        return Response.ok(this.pirateList ).build();
    }

    @PUT
    @Path("editNickname/{old}/{new}")
    @Produces(MediaType.APPLICATION_JSON)
        public Response editNickname(@PathParam("old") String oldNickname, @PathParam("new") String newNickname){
        Optional<Pirate_R> pirate = this.pirateList.stream().filter(p -> p.getNickname().equals(oldNickname)).findFirst();
        if (pirate.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        pirate.get().setNickname(newNickname);
        return Response.ok(pirate.get().toString() + "old Nickname: " + oldNickname +//
                 " changed to " + newNickname ).build();
    }

    @DELETE
    @Path("deletePirate/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePirate(@PathParam("name") String name){
        Optional<Pirate_R> pirate = this.pirateList.stream().filter(p -> p.getName().equals(name)).findFirst();
        if (pirate.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        pirateList.remove(pirate.get());
        return Response.ok(pirate.get().toString() + "removed" ).build();
    }

}
