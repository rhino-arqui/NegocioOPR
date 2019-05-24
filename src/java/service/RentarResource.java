/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author root
 */
public class RentarResource {

    private String id;

    /**
     * Creates a new instance of RentarResource
     */
    private RentarResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the RentarResource
     */
    public static RentarResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of RentarResource class.
        return new RentarResource(id);
    }

    /**
     * Retrieves representation of an instance of service.RentarResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RentarResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource RentarResource
     */
    @DELETE
    public void delete() {
    }
}
