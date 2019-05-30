/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Client;
import entities.Property;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juan Pablo Peñaloza
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "NegocioOPRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    /**
     * Returns true if the username and password matches the ones 
     * stored in the database.
     * @param username
     * @param password
     * @return 
     */
    public boolean isCredentialValid(String username, String password) {
        List<Client> result = em.createNamedQuery("Client.findByUsername")
                .setParameter("username", username)
                .getResultList();
        if(result.isEmpty()) return false;
        Client client = result.get(0);
        return client.getPassword().equals(password);
    }
    
    
}
