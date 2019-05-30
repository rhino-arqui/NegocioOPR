/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import entities.Client;
import facade.ClientFacade;
import facade.PropertyFacade;
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
 * @author JuanPablo
 */
@Path("/clients")
public class ClientsResource {

    ClientFacade clientFacade = lookupClientFacadeBean();
    Gson gson = null;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClientsResource
     */
    public ClientsResource() {
        this.gson = new GsonBuilder().create();
    }

    /**
     * Gets all the clients
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return this.gson.toJson(this.clientFacade.findAll());
    }
    
    /**
     * Returns the ID of a client who is trying to login
    */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String content) {
        Client clientCredentials = null;
        try {
            clientCredentials = this.gson.fromJson(content, Client.class);
        } catch (JsonSyntaxException jsonError) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonError.getMessage()).build();
        }
        boolean isCredentialValid = this.clientFacade.isCredentialValid(clientCredentials.getUsername(), clientCredentials.getPassword());
        if(!isCredentialValid) return Response.status(Response.Status.UNAUTHORIZED).entity("Credentials not valid").build();
        return Response.status(Response.Status.ACCEPTED).entity("Welcome").build();
    }

    /**
     * Creates a new client with the information provided in the content.
     * @param content
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        Client newClient = null;
        try {
            newClient = this.gson.fromJson(content, Client.class);
        } catch (JsonSyntaxException jsonError) {
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonError.getMessage()).build();
        }
        
        try {
            this.clientFacade.create(newClient);
            return Response.created(context.getBaseUri()).build();
        } catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getCause()).build();
        }
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public ClientResource getClientResource(@PathParam("id") String id) {
        return ClientResource.getInstance(id);
    }

    private ClientFacade lookupClientFacadeBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ClientFacade) c.lookup("java:global/NegocioOPR/ClientFacade!facade.ClientFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private class LoginResponse {
        private String message;
        public LoginResponse(String message) {
            this.message = message;
        }
    }
    
}
