/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entities.RentRecord;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class TimerLimitDate {

    @EJB
    private RentRecordFacade rentRecordFacade;

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/10", second = "0", persistent = false)
    
    public void myTimer() {
        System.out.println("[TIMER]Timer time limit: " + new Date());
        this.removeConfirmed();
        System.out.println("[TIMER_END]Timer time limit: " + new Date());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void removeConfirmed() {
        List<RentRecord> rents = this.rentRecordFacade.getRecordsNotConfirmed();
        System.out.println("[TIMER]Timer time limit: I'm going to delete "+ rents.size()+" rent(s).");
       this.rentRecordFacade.deleteRecordsNotConfirmed();
       // TODO - send email or anything?
    }
    
}
