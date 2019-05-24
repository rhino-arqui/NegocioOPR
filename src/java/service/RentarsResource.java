/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import entities.RentRecord;
import facade.RentRecordFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author root
 */
@Path("/rentar")
public class RentarsResource {

    RentRecordFacade rentRecordFacade = lookupRentRecordFacadeBean();
    Gson gson = null;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RentarsResource
     */
    public RentarsResource() {
        this.gson = new GsonBuilder().create();
    }

    /**
     * Retrieves representation of an instance of service.RentarsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST method for creating an instance of RentarResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        System.out.println(content);
        RentRecord rent = null ; 
        
        try{
            rent = this.gson.fromJson(content, RentRecord.class);
        }catch(JsonSyntaxException jsonError)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonError.getMessage()).build();
        }
        try{
            rent = this.rentRecordFacade.createRent(rent);
            String response = this.gson.toJson(rent);
            return Response.created(context.getBaseUri()).entity(response).build();
            //return Response.accepted().build();
        }catch(Exception e){
            //System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public RentarResource getRentarResource(@PathParam("id") String id) {
        return RentarResource.getInstance(id);
    }

    private RentRecordFacade lookupRentRecordFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RentRecordFacade) c.lookup("java:global/NegocioOPR/RentRecordFacade!facade.RentRecordFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
