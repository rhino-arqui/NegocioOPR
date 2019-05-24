/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.RentRecord;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless
public class RentRecordFacade extends AbstractFacade<RentRecord> {

    @PersistenceContext(unitName = "NegocioOPRPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RentRecordFacade() {
        super(RentRecord.class);
    }
    
    
    public RentRecord createRent(RentRecord entity) throws Exception {
        // TODO - financiera throw exeption is more simple :p
        verifyRent(entity);
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
        String queryStr = "SELECT rr FROM RentRecord rr "+
        "    WHERE rr.clientId = :client_id AND rr.propertyId = :property_id"+
        " ORDER BY  rr.rentalDate DESC ";
        //") WHERE ROWNUM = 1;";
        Query query = this.getEntityManager().createQuery(queryStr);
        query.setParameter("client_id", entity.getClientId());
        query.setParameter("property_id", entity.getPropertyId());
        return (RentRecord) query.getResultList().get(0);
    }

    public void confirm(BigDecimal id) {
        RentRecord rent = this.find(id);
        rent.setConfirmedDate(new Date());
        this.edit(rent);
    }

    private void verifyRent(RentRecord entity) throws Exception {
        String queryStr ="SELECT rr FROM RentRecord rr WHERE rr.propertyId = :property_id";
        
        Query query = this.getEntityManager().createQuery(queryStr);
        
        query.setParameter("property_id", entity.getPropertyId());
        if(!query.getResultList().isEmpty()){
            
             throw new Exception("Is rented");
        }
        
    }
    
}
