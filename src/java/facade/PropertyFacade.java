/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Property;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import service.PropertyResource;

/**
 *
 * @author root
 */
@Stateless
public class PropertyFacade extends AbstractFacade<Property> {

    @EJB
    private ClientFacade clientFacade;

    @PersistenceContext(unitName = "NegocioOPRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropertyFacade() {
        super(Property.class);
    }
    

    @Override
    public void create(Property entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        // TODO - send email to information the creation
    }

    public List<Property> searchByCedula(String id) {
        String queryStr = "SELECT p FROM  Property p"+
        "    WHERE p.clientId = :client_id ";
        //"   ORDER BY  rr.rentalDate DESC ";
        Query query = this.getEntityManager().createQuery(queryStr);
        query.setParameter("client_id",this.clientFacade.getById(id) );
        return query.getResultList();
    }
    
    
    
}
