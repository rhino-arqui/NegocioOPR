/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import entities.Property;
import facade.PropertyFacade;
import java.util.List;
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
@Path("/properties")
public class PropertiesResource {

    PropertyFacade propertyFacade = lookupPropertyFacadeBean();
    Gson gson = null;
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of PropertiesResource
     */
    public PropertiesResource() {
        
        this.gson = new GsonBuilder().create();
    }

    /**
     * Retrieves representation of an instance of service.PropertiesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //System.out.println(this.propertyFacade.findAll().toString());
        return this.gson.toJson(this.propertyFacade.findAll());
        //return this.propertyFacade.findAll().toString();
        //return Response.status(Response.Status.OK).entity(this.propertyFacade.findAll()).build();
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
    }

    /**
     * POST method for creating an instance of PropertyResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        System.out.println(content);
        Property property = null;
        try{
            property = this.gson.fromJson(content, Property.class);
        }catch(JsonSyntaxException jsonError)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonError.getMessage()).build();
        }
        
        try{
            this.propertyFacade.create(property);
            return Response.created(context.getBaseUri()).build();
            //return Response.accepted().build();
        }catch(Exception e){
            //System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getCause()).build();
        }
    }

    /**
     * Sub-resource locator method for {id}
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearchByCedula(@PathParam("id") String id) {
        List<Property> properties = this.propertyFacade.searchByCedula(id);
        String json = gson.toJson(properties);
        return json;
    }

    private PropertyFacade lookupPropertyFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PropertyFacade) c.lookup("java:global/NegocioOPR/PropertyFacade!facade.PropertyFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
