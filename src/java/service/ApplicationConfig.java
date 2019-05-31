/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author root
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.ClientResource.class);
        resources.add(service.ClientsResource.class);
        resources.add(service.ConfirmResource.class);
        resources.add(service.ConfirmsResource.class);
        resources.add(service.PropertiesResource.class);
        resources.add(service.PropertyResource.class);
        resources.add(service.RentarResource.class);
        resources.add(service.RentarsResource.class);
    }
    
}
