/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.RentRecord;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class AsynchronousRentRecord {

    @EJB
    private RentRecordFacade rentRecordFacade;

    @Asynchronous
    public  Future<List<RentRecord>> getRentRecords(){
        try {
            TimeUnit.SECONDS.sleep(10);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(AsynchronousRentRecord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AsyncResult<>( this.rentRecordFacade.findAll());
    }
    
}
