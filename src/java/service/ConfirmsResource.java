/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import entities.RentRecord;
import facade.RentRecordFacade;
import java.util.Date;
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
@Path("/confirm")
public class ConfirmsResource {

    RentRecordFacade rentRecordFacade = lookupRentRecordFacadeBean();

    @Context
    private UriInfo context;
    
    Gson gson = null;
    /**
     * Creates a new instance of ConfirmsResource
     */
    public ConfirmsResource() {
    }

    /**
     * Retrieves representation of an instance of service.ConfirmsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST method for creating an instance of ConfirmResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response postXml(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public Response getConfirmResource(@PathParam("id") String id) {
        this.rentRecordFacade.confirm(id);
        return Response.accepted().entity("Confirmed.").build();
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
