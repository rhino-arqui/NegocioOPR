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
public class ConfirmResource {

    private String id;

    /**
     * Creates a new instance of ConfirmResource
     */
    private ConfirmResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the ConfirmResource
     */
    public static ConfirmResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ConfirmResource class.
        return new ConfirmResource(id);
    }

    /**
     * Retrieves representation of an instance of service.ConfirmResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ConfirmResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource ConfirmResource
     */
    @DELETE
    public void delete() {
    }
}
