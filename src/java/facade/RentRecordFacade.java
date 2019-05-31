/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.Property;
import entities.RentRecord;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Stateless
public class RentRecordFacade extends AbstractFacade<RentRecord> {

    @EJB
    private PropertyFacade propertyFacade;

    @Resource(mappedName = "jms/myTopic")
    private Topic myTopic;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

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
        this.sendJMSMessageToMyTopic(this.propertyFacade.find(rent.getPropertyId().getId()));
    }

    private void verifyRent(RentRecord entity) throws Exception {
        String queryStr ="SELECT rr FROM RentRecord rr WHERE rr.propertyId = :property_id";
        
        Query query = this.getEntityManager().createQuery(queryStr);
        
        query.setParameter("property_id", entity.getPropertyId());
        if(!query.getResultList().isEmpty()){
            
             throw new Exception("Is rented");
        }
        
    }
    
    public List<RentRecord> getRecordsNotConfirmed(){
        String queryStr ="SELECT rr FROM RentRecord rr WHERE rr.confirmedDate is null";
        
        Query query = this.getEntityManager().createQuery(queryStr);
        return query.getResultList();
    }
    
    public int deleteRecordsNotConfirmed(){
        String queryStr ="DELETE FROM RentRecord rr WHERE rr.confirmedDate is null";
        
        Query query = this.getEntityManager().createQuery(queryStr);
        return query.executeUpdate();
        
    }

    private void sendJMSMessageToMyTopic(Property messageData) {
        context.createProducer().send(myTopic, messageData);
    }
    
}
