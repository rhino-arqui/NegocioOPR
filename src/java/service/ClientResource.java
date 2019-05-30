/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.ClientFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author JuanPablo
 */
public class ClientResource {

    ClientFacade clientFacade = lookupClientFacadeBean();
    Gson gson = null;

    private String id;

    /**
     * Creates a new instance of ClientResource
     */
    private ClientResource(String id) {
        this.id = id;
        this.gson = new GsonBuilder().create();
    }

    /**
     * Get instance of the ClientResource
     */
    public static ClientResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ClientResource class.
        return new ClientResource(id);
    }

    /**
     * Retrieves representation of an instance of service.ClientResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return this.gson.toJson(this.clientFacade.find(id));
    }

    /**
     * PUT method for updating or creating an instance of ClientResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource ClientResource
     */
    @DELETE
    public void delete() {
    }
    

    private ClientFacade lookupClientFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ClientFacade) c.lookup("java:global/NegocioOPR/ClientFacade!facade.ClientFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
